package com.mitocode.microservices.product_service.messaging;

public record StockCommand(String sagaId, String action, String productId, Integer quantity) {
}
