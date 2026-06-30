package com.mitocode.microservices.product_service.service.repository;

import com.mitocode.microservices.commonmodels.model.entity.ProductEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<ProductEntity,String> {
}
