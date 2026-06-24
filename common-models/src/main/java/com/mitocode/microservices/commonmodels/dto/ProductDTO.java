package com.mitocode.microservices.commonmodels.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private String productId;
    private String productName;
    private String productType;
    private Long price;
    private Integer stock;
}
