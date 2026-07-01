package com.mitocode.microservices.common_models.client_service.proxy.openfeign;

import com.mitocode.microservices.common_models.client_service.model.request.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "cloud-gateway")
public interface CloudGatewayFeign {

    @GetMapping("/api/product-service/product")
    List<ProductDTO> getAllProducts();

    @GetMapping("/api/product-service/product/{productId}")
    ProductDTO getProductById(@PathVariable("productId") String productId);

    @PostMapping("/api/product-service/product")
    ProductDTO saveProduct(@RequestBody ProductDTO productDTO);

    @PutMapping("/api/product-service/product/{productId}")
    ProductDTO updateProduct(@PathVariable("productId") String productId, @RequestBody ProductDTO productDTO);

    @DeleteMapping("/api/product-service/product/{productId}")
    void deleteProduct(@PathVariable("productId") String productId);
}
