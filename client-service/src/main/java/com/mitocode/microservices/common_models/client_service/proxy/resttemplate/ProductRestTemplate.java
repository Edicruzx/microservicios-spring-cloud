package com.mitocode.microservices.common_models.client_service.proxy.resttemplate;


import com.mitocode.microservices.common_models.client_service.model.request.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ProductRestTemplate {


    private final RestTemplate restTemplate;

    public List<ProductDTO> getAllProducts() {
        ProductDTO[] products = restTemplate.getForObject("http://localhost:9001/product",ProductDTO[].class
        );

        return Arrays.asList(Objects.requireNonNull(products));
    }
}