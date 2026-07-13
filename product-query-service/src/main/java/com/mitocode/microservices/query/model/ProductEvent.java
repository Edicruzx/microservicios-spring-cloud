package com.mitocode.microservices.query.model;

import java.time.Instant;

public record ProductEvent(String eventId, String eventType, Instant occurredAt, ProductView product) { }
