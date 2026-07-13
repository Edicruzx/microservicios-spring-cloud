package com.mitocode.microservices.query.messaging;

import com.mitocode.microservices.query.model.ProductEvent;
import com.mitocode.microservices.query.repository.ProductViewRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ProductEventListener {
    private final ProductViewRepository repository;
    public ProductEventListener(ProductViewRepository repository) { this.repository = repository; }
    @KafkaListener(topics = "product-events", groupId = "product-query-service")
    public void consume(ProductEvent event) {
        if ("PRODUCT_DELETED".equals(event.eventType())) repository.deleteById(event.product().productId());
        else repository.save(event.product());
    }
}
