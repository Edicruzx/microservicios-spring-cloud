package com.mitocode.microservices.common_models.client_service.expose.web;

import com.mitocode.microservices.common_models.client_service.model.request.ProductDTO;
import com.mitocode.microservices.common_models.client_service.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService; // dependencia

    @GetMapping("/product")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(clientService.getAllProducts());
    }


    @GetMapping("/product/{productId}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable String productId) {
        return ResponseEntity.ok(clientService.getProductById(productId));
    }
    @PostMapping("/product")
    public ResponseEntity<ProductDTO> saveProduct(@Valid @RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(clientService.saveProduct(productDTO));
    }

    @PutMapping("/product/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable String productId, @Valid @RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(clientService.updateProduct(productId, productDTO));
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String productId) {
        clientService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }
}
