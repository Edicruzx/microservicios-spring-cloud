package com.mitocode.microservices.product_service.messaging;

import com.mitocode.microservices.product_service.model.dto.ProductDTO;

import java.time.Instant;

public record ProductEvent(
        String eventId,
        String eventType,
        Instant occurredAt,
        ProductDTO product
) {
}
