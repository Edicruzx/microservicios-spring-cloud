package com.mitocode.microservices.query.repository;

import com.mitocode.microservices.query.model.ProductView;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductViewRepository extends MongoRepository<ProductView, String> { }
