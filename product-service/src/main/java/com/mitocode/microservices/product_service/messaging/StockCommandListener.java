package com.mitocode.microservices.product_service.messaging;

import com.mitocode.microservices.commonmodels.model.entity.ProductEntity;
import com.mitocode.microservices.product_service.model.dto.ProductDTO;
import com.mitocode.microservices.product_service.service.repository.ProductRepository;
import com.mitocode.microservices.product_service.util.UtilMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class StockCommandListener {
    private final ProductRepository repository;
    private final UtilMapper mapper;
    private final ProductEventPublisher events;
    private final KafkaTemplate<String, Object> kafka;

    public StockCommandListener(ProductRepository repository, UtilMapper mapper, ProductEventPublisher events, KafkaTemplate<String, Object> kafka) {
        this.repository = repository;
        this.mapper = mapper;
        this.events = events;
        this.kafka = kafka;
    }

    @KafkaListener(topics = "stock-commands", groupId = "product-stock-saga")
    public void consume(StockCommand command) {
        try {
            ProductEntity entity = repository.findById(command.productId()).orElseThrow(() -> new IllegalStateException("Producto no encontrado"));
            int delta = "RELEASE".equals(command.action()) ? command.quantity() : -command.quantity();
            int newStock = entity.getStock() + delta;
            if (newStock < 0) throw new IllegalStateException("Stock insuficiente");
            entity.setStock(newStock);
            ProductEntity saved = repository.save(entity);
            ProductDTO dto = mapper.convertEntityToDTO(saved);
            events.publish("PRODUCT_UPDATED", dto);
            send(new StockResult(command.sagaId(), true, command.action(), "Stock procesado correctamente"));
        } catch (Exception exception) {
            send(new StockResult(command.sagaId(), false, command.action(), exception.getMessage()));
        }
    }

    private void send(StockResult result) { kafka.send("stock-results", result.sagaId(), result); }
}
