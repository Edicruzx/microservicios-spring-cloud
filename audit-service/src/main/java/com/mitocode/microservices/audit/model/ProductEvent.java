package com.mitocode.microservices.audit.model;

import java.time.Instant;

public record ProductEvent(String eventId, String eventType, Instant occurredAt, ProductSnapshot product) {
}
