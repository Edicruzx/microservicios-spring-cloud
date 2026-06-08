package com.mitocode.microservices.client_service.proxy.openfeign;

import com.mitocode.microservices.client_service.model.request.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


@FeignClient(name = "cloud-gateway")
public interface CloudGatewayFeign {

    @GetMapping("/api/product-service/product")
    List<ProductDTO> getAllProducts ();

    @PostMapping("/api/product-service/product")
    ProductDTO saveProduct (@RequestBody ProductDTO productDTO);



}