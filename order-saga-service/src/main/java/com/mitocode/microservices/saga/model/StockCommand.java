package com.mitocode.microservices.saga.model;

public record StockCommand(String sagaId, String action, String productId, Integer quantity) { }
