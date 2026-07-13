package com.mitocode.microservices.product_service;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.config.TopicBuilder;

@SpringBootApplication
@SecurityScheme(name = "mitocode", scheme = "bearer", bearerFormat = "JWT", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class ProductServiceApplication {

    @Bean
    NewTopic productEventsTopic() {
        return TopicBuilder.name("product-events").partitions(1).replicas(1).build();
    }

    @Bean
    NewTopic stockCommandsTopic() {
        return TopicBuilder.name("stock-commands").partitions(1).replicas(1).build();
    }

    @Bean
    NewTopic stockResultsTopic() {
        return TopicBuilder.name("stock-results").partitions(1).replicas(1).build();
    }

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }
}
