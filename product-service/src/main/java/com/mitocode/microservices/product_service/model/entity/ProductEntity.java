package com.mitocode.microservices.product_service.model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Setter
@Getter
@Builder
@Document (collection = "product")
public class ProductEntity {

    @Id
    private String productId;
    private String productName;
    private Long price;
    private Integer stock;
    private String productType;
    private Integer port;


}

