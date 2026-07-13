package com.mitocode.microservices.audit.model;

public record ProductSnapshot(String productId, String productName, Long price, Integer stock, String productType, Integer port) { }
