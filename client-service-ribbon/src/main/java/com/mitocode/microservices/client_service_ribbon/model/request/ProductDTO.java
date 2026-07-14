package com.mitocode.microservices.client_service_ribbon.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ProductDTO {

    private String productId;
    private String productName;
    private Long price;
    private Integer stock;
    private String productType;
    private Integer port;
}
