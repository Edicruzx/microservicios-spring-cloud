package com.mitocode.microservices.saga.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record CreateOrderRequest(@NotBlank String productId, @Positive Integer quantity) { }
