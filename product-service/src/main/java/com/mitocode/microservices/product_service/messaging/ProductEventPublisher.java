package com.mitocode.microservices.product_service.messaging;

import com.mitocode.microservices.product_service.model.dto.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProductEventPublisher {

    public static final String TOPIC = "product-events";
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publish(String eventType, ProductDTO product) {
        ProductEvent event = new ProductEvent(UUID.randomUUID().toString(), eventType, Instant.now(), product);
        kafkaTemplate.send(TOPIC, product.getProductId(), event);
    }
}
