package com.mitocode.microservices.saga.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;

@Document("order_sagas")
public record OrderSaga(@Id String sagaId, String productId, Integer quantity, SagaStatus status, String message, Instant updatedAt) { }
