package com.mitocode.microservices.audit.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document("product_audit")
public record AuditEntry(@Id String eventId, String eventType, Instant occurredAt, ProductSnapshot product) {
    public static AuditEntry from(ProductEvent event) {
        return new AuditEntry(event.eventId(), event.eventType(), event.occurredAt(), event.product());
    }
}
