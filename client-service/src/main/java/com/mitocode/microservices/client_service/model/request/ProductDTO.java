package com.mitocode.microservices.client_service.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {

    private String productId;
    private String productName;
    private Long price;
    private Integer stock;
    private String productType;
    private Integer port;


}
