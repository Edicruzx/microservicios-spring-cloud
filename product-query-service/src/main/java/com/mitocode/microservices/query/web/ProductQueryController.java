package com.mitocode.microservices.query.web;

import com.mitocode.microservices.query.model.ProductView;
import com.mitocode.microservices.query.repository.ProductViewRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/product-query")
public class ProductQueryController {
    private final ProductViewRepository repository;
    public ProductQueryController(ProductViewRepository repository) { this.repository = repository; }
    @GetMapping public List<ProductView> findAll() { return repository.findAll(); }
    @GetMapping("/{id}") public ResponseEntity<ProductView> findById(@PathVariable String id) { return ResponseEntity.of(repository.findById(id)); }
}
