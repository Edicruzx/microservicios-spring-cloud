package com.mitocode.microservices.product_service;

import com.mitocode.microservices.product_service.model.entity.ProductEntity;
import com.mitocode.microservices.product_service.service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootApplication
@RequiredArgsConstructor
public class ProductServiceApplication implements CommandLineRunner {

	private final ProductRepository productRepository;

	@Autowired
	private MongoTemplate mongoTemplate;

	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}

	@Value("${spring.data.mongodb.uri}")
	private String mongoUri;

	@Override
	public void run(String... args) throws Exception {

		System.out.println("BASE ACTUAL: " + mongoTemplate.getDb().getName());

		productRepository.deleteAll();

		productRepository.save(ProductEntity.builder()
				.productId("P00001")
				.productName("Microservicios Básico")
				.productType("Curso")
				.price(200L)
				.stock(50)
				.build());

		productRepository.save(ProductEntity.builder()
				.productId("P00002")
				.productName("Microservicios Avanzados")
				.productType("Curso")
				.price(300L)
				.stock(40)
				.build());




	}

}