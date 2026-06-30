package com.mitocode.microservices.common_models.client_service.service;

import com.mitocode.microservices.common_models.client_service.model.request.ProductDTO;
import com.mitocode.microservices.common_models.client_service.proxy.openfeign.CloudGatewayFeign;
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

    @CircuitBreaker(name = "product-cb", fallbackMethod = "getAlternativeProducts")
    public List<ProductDTO> getAllProducts() {
        return cloudGatewayFeign.getAllProducts();
    }

    private List<ProductDTO> getAlternativeProducts(Throwable e) {
        log.info(e.getMessage());
        List<ProductDTO> listProducts = new ArrayList<>();
        listProducts.add(ProductDTO.builder().productId("P9999").productName("Product Fake").productType("Fake").stock(5).build());
        return listProducts;
    }

    @CircuitBreaker(name = "product-cb", fallbackMethod = "getAlternativeSaveProduct")
    public ProductDTO saveProduct(ProductDTO productDTO) {
        return cloudGatewayFeign.saveProduct(productDTO);
    }

    private ProductDTO getAlternativeSaveProduct(ProductDTO productDTO, Throwable e) {
        log.info(e.getMessage());
        return ProductDTO.builder().productId("P9999").productName("Product Fake").productType("Fake").stock(5).build();
    }

    @CircuitBreaker(name = "product-cb", fallbackMethod = "getAlternativeUpdateProduct")
    public ProductDTO updateProduct(String productId, ProductDTO productDTO) {
        return cloudGatewayFeign.updateProduct(productId, productDTO);
    }

    private ProductDTO getAlternativeUpdateProduct(String productId, ProductDTO productDTO, Throwable e) {
        log.info(e.getMessage());
        return ProductDTO.builder().productId(productId).productName("Product Fake").productType("Fake").stock(5).build();
    }

    @CircuitBreaker(name = "product-cb", fallbackMethod = "getAlternativeDeleteProduct")
    public void deleteProduct(String productId) {
        cloudGatewayFeign.deleteProduct(productId);
    }

    private void getAlternativeDeleteProduct(String productId, Throwable e) {
        log.info(e.getMessage());
    }
}
