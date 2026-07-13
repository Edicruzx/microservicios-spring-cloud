package com.mitocode.microservices.saga.service;

import com.mitocode.microservices.saga.model.*;
import com.mitocode.microservices.saga.repository.OrderSagaRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.UUID;

@Service
public class OrderSagaService {
    private final OrderSagaRepository repository;
    private final KafkaTemplate<String, Object> kafka;
    public OrderSagaService(OrderSagaRepository repository, KafkaTemplate<String, Object> kafka) { this.repository = repository; this.kafka = kafka; }

    public OrderSaga start(CreateOrderRequest request) {
        String id = UUID.randomUUID().toString();
        OrderSaga saga = repository.save(new OrderSaga(id, request.productId(), request.quantity(), SagaStatus.STARTED, "Solicitando reserva de stock", Instant.now()));
        kafka.send("stock-commands", id, new StockCommand(id, "RESERVE", request.productId(), request.quantity()));
        return saga;
    }

    public OrderSaga compensate(String id) {
        OrderSaga saga = get(id);
        OrderSaga updated = repository.save(new OrderSaga(id, saga.productId(), saga.quantity(), SagaStatus.COMPENSATING, "Liberando stock", Instant.now()));
        kafka.send("stock-commands", id, new StockCommand(id, "RELEASE", saga.productId(), saga.quantity()));
        return updated;
    }

    @KafkaListener(topics = "stock-results", groupId = "order-saga-service")
    public void result(StockResult result) {
        repository.findById(result.sagaId()).ifPresent(saga -> {
            SagaStatus status = result.success()
                    ? ("RELEASE".equals(result.action()) ? SagaStatus.COMPENSATED : SagaStatus.COMPLETED)
                    : SagaStatus.FAILED;
            repository.save(new OrderSaga(saga.sagaId(), saga.productId(), saga.quantity(), status, result.message(), Instant.now()));
        });
    }

    public OrderSaga get(String id) { return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Saga no encontrada: " + id)); }
}
