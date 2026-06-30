package com.mitocode.microservices.commonmodels.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "product")
public class ProductEntity extends GenericEntity<String> {

    @Id
    private String productId;

    private String productName;
    private Long price;
    private Integer stock;
    private String productType;
    private Integer port;
}