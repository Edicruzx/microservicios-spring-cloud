package com.mitocode.microservices.saga.model;

public record StockResult(String sagaId, boolean success, String action, String message) { }
