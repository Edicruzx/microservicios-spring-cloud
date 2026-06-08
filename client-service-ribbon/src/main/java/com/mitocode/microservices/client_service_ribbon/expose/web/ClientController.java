package com.mitocode.microservices.client_service_ribbon.expose.web;

import com.mitocode.microservices.client_service_ribbon.model.request.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class ClientController {

    private final RestTemplate restTemplate;

    @GetMapping("/product")
    public List<ProductDTO> getProductService() {
        return List.of(Objects.requireNonNull(restTemplate.getForObject("http://product-service/product",ProductDTO[].class
                )
        ));
    }
}