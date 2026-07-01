package com.mitocode.microservices.common_models.client_service.expose.web.error;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final ObjectMapper objectMapper;

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ApiError> handleFeignException(
            FeignException exception,
            HttpServletRequest request
    ) {
        String responseBody = exception.contentUTF8();

        if (responseBody != null && !responseBody.isBlank()) {
            try {
                JsonNode body = objectMapper.readTree(responseBody);
                ApiError error = new ApiError(
                        body.path("status").asInt(exception.status()),
                        body.path("message").asText(exception.getMessage()),
                        request.getRequestURI()
                );

                return ResponseEntity.status(exception.status()).body(error);
            } catch (Exception ignored) {
                // If the downstream service did not return JSON, build our standard API error.
            }
        }

        ApiError error = new ApiError(
                exception.status(),
                exception.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(exception.status()).body(error);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiError> handleResponseStatusException(
            ResponseStatusException exception,
            HttpServletRequest request
    ) {
        int statusCode = exception.getStatusCode().value();
        String message = exception.getReason() != null ? exception.getReason() : "Error en la solicitud";
        ApiError error = new ApiError(statusCode, message, request.getRequestURI());

        return ResponseEntity.status(statusCode).body(error);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiError> handleMethodNotSupported(
            HttpRequestMethodNotSupportedException exception,
            HttpServletRequest request
    ) {
        ApiError error = new ApiError(
                HttpStatus.METHOD_NOT_ALLOWED.value(),
                "Metodo no permitido para esta ruta",
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(
            Exception exception,
            HttpServletRequest request
    ) {
        ApiError error = new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Error interno del servidor",
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}