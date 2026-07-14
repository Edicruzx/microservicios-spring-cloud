package com.mitocode.microservices.audit.messaging;

import com.mitocode.microservices.audit.model.AuditEntry;
import com.mitocode.microservices.audit.model.ProductEvent;
import com.mitocode.microservices.audit.repository.AuditRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ProductEventListener {
    private final AuditRepository repository;

    public ProductEventListener(AuditRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(topics = "product-events", groupId = "audit-service")
    public void consume(ProductEvent event) {
        repository.save(AuditEntry.from(event));
    }
}
