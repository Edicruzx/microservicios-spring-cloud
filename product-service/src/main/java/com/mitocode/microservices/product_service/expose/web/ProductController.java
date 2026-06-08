package com.mitocode.microservices.product_service.expose.web;

import com.mitocode.microservices.product_service.model.dto.ProductDTO;
import com.mitocode.microservices.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping(value = "/product")
    public ResponseEntity<List<ProductDTO>> getAllProducts() throws InterruptedException {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PostMapping(value = "/product")
    public ResponseEntity<ProductDTO> saveProduct(@RequestBody ProductDTO productDTO) {
       log.info(productDTO.toString());
        return ResponseEntity.ok(productService.saveProduct(productDTO));
    }
}