package com.mitocode.microservices.product_service.expose.web.error;

public record ApiError(
        int status,
        String message,
        String path
) {
}