package com.mitocode.microservices.query.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("product_views")
public record ProductView(@Id String productId, String productName, Long price, Integer stock, String productType, Integer port) { }
