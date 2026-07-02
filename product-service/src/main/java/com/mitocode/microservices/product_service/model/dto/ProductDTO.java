package com.mitocode.microservices.product_service.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    @NotBlank(message = "productId es obligatorio")
    private String productId;

    @NotBlank(message = "productName es obligatorio")
    private String productName;

    @NotNull(message = "price es obligatorio")
    @Positive(message = "price debe ser mayor que cero")
    private Long price;

    @NotNull(message = "stock es obligatorio")
    @PositiveOrZero(message = "stock no puede ser negativo")
    private Integer stock;

    @NotBlank(message = "productType es obligatorio")
    private String productType;

    private Integer port;
}