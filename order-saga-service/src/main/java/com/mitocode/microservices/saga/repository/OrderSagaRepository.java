package com.mitocode.microservices.saga.repository;

import com.mitocode.microservices.saga.model.OrderSaga;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderSagaRepository extends MongoRepository<OrderSaga, String> { }
