package com.mitocode.microservices.client_service.expose.web;

import com.mitocode.microservices.client_service.model.request.ProductDTO;
import com.mitocode.microservices.client_service.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping("/product")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(clientService.getAllProducts());
    }

    @PostMapping("/product")
    public ResponseEntity<ProductDTO>saveProduct (@RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(clientService.saveProduct(productDTO));
    }

}