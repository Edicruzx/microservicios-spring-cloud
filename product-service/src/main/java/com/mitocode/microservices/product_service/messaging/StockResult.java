package com.mitocode.microservices.product_service.messaging;

public record StockResult(String sagaId, boolean success, String action, String message) {
}
