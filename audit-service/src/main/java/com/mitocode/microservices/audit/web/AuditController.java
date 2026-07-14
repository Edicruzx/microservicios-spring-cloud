package com.mitocode.microservices.audit.web;

import com.mitocode.microservices.audit.model.AuditEntry;
import com.mitocode.microservices.audit.repository.AuditRepository;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/audit")
public class AuditController {
    private final AuditRepository repository;

    public AuditController(AuditRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<AuditEntry> findAll() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "occurredAt"));
    }
}
