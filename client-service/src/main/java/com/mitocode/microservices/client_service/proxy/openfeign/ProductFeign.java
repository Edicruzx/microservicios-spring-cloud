package com.mitocode.microservices.client_service.proxy.openfeign;


import com.mitocode.microservices.client_service.model.request.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


import java.util.List;

@FeignClient(name = "product-service")
public interface ProductFeign {

    @GetMapping("/product")
    List<ProductDTO> getAllProducts();

    @PostMapping("/product")
    ProductDTO saveProduct(ProductDTO productDTO);

}
