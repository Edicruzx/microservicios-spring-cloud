package com.mitocode.microservices.common_models.client_service.proxy.openfeign;

import com.mitocode.microservices.common_models.client_service.model.request.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "product-service")
public interface ProductFeign {

    @GetMapping("/product")
    List<ProductDTO> getAllProducts();

    @PostMapping("/product")
    ProductDTO saveProduct(@RequestBody ProductDTO productDTO);

    @PutMapping("/product/{productId}")
    ProductDTO updateProduct(@PathVariable("productId") String productId, @RequestBody ProductDTO productDTO);

    @DeleteMapping("/product/{productId}")
    void deleteProduct(@PathVariable("productId") String productId);
}
