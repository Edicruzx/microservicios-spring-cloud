package com.mitocode.microservices.common_models.client_service.expose.web.error;

public record ApiError(
        int status,
        String message,
        String path
) {
}