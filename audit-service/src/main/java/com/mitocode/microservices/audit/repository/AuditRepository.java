package com.mitocode.microservices.audit.repository;

import com.mitocode.microservices.audit.model.AuditEntry;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AuditRepository extends MongoRepository<AuditEntry, String> { }
