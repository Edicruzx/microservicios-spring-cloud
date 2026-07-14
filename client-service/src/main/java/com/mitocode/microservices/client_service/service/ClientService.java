package com.mitocode.microservices.client_service.service;


import com.mitocode.microservices.client_service.model.request.ProductDTO;
import com.mitocode.microservices.client_service.proxy.openfeign.CloudGatewayFeign;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientService {

    private final CloudGatewayFeign cloudGatewayFeign;
    private final CircuitBreakerFactory circuitBreakerFactory;

    /*

    -----------Circuit breaker factory-----------
    public List<ProductDTO> getAllProducts() {
        return circuitBreakerFactory.create("mitocode")
                .run(
                        () -> cloudGatewayFeign.getAllProducts(),
                        e -> getAlternativeProducts(e)
                );
    }

    public ProductDTO saveProduct(ProductDTO productDTO) {
        return circuitBreakerFactory.create("mitocode")
                .run(
                        () -> cloudGatewayFeign.saveProduct(productDTO),
                        e -> getAlternativeSaveProduct(productDTO, e)
                );
    }

    private List<ProductDTO> getAlternativeProducts(Throwable e) {
        log.info(e.getMessage());

        List<ProductDTO> lstProducts = new ArrayList<>();

        ProductDTO productDTO = ProductDTO.builder()
                .productId("P99999")
                .productName("Product Fake")
                .productType("Fake")
                .stock(5)
                .build();

        lstProducts.add(productDTO);

        return lstProducts;
    }

    private ProductDTO getAlternativeSaveProduct(ProductDTO productDTO, Throwable e) {
        log.info(e.getMessage());

        return ProductDTO.builder()
                .productId("P99999")
                .productName("Product Fake")
                .productType("Fake")
                .stock(5)
                .build();
    }
*/

    @CircuitBreaker(name = "product-cb", fallbackMethod = "getAlternativeProducts")
    public List<ProductDTO> getAllProducts() {
        return cloudGatewayFeign.getAllProducts();
    }

    private List<ProductDTO> getAlternativeProducts(Throwable e) {
        log.info(e.getMessage());

        List<ProductDTO> listProducts = new ArrayList<>();

        ProductDTO productDTO = ProductDTO.builder()
                .productId("P9999")
                .productName("Product Fake")
                .productType("Fake")
                .stock(5)
                .build();

        listProducts.add(productDTO);

        return listProducts;
    }

    @CircuitBreaker(name = "product-cb", fallbackMethod = "getAlternativeSaveProduct")
    public ProductDTO saveProduct(ProductDTO productDTO) {
        return cloudGatewayFeign.saveProduct(productDTO);
    }

    private ProductDTO getAlternativeSaveProduct(ProductDTO productDTO, Throwable e) {
        log.info(e.getMessage());

        return ProductDTO.builder()
                .productId("P9999")
                .productName("Product Fake")
                .productType("Fake")
                .stock(5)
                .build();
    }
}
