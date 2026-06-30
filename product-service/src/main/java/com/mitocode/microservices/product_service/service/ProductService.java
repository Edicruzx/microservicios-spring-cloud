package com.mitocode.microservices.product_service.service;

import com.mitocode.microservices.commonmodels.model.entity.ProductEntity;
import com.mitocode.microservices.product_service.model.dto.ProductDTO;
import com.mitocode.microservices.product_service.service.repository.ProductRepository;
import com.mitocode.microservices.product_service.util.UtilMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class ProductService {

    @Value("${server.port}")
    private Integer port;

    private final UtilMapper utilMapper;
    private final ProductRepository productRepository;

    public List<ProductDTO> getAllProducts() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(500L);

        Iterable<ProductEntity> itProducts = productRepository.findAll();
        return StreamSupport.stream(itProducts.spliterator(), false)
                .map(productEntity -> {
                    ProductDTO productDTO = utilMapper.convertEntityToDTO(productEntity);
                    productDTO.setPort(port);
                    return productDTO;
                })
                .collect(Collectors.toList());
    }

    public ProductDTO saveProduct(ProductDTO productDTO) {
        ProductEntity productEntity = utilMapper.convertDTOToEntity(productDTO);
        productRepository.save(productEntity);
        productDTO.setPort(port);
        return productDTO;
    }

    public ProductDTO updateProduct(String productId, ProductDTO productDTO) {
        productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado: " + productId));

        productDTO.setProductId(productId);
        ProductEntity productEntity = utilMapper.convertDTOToEntity(productDTO);
        ProductEntity savedProduct = productRepository.save(productEntity);

        ProductDTO response = utilMapper.convertEntityToDTO(savedProduct);
        response.setPort(port);
        return response;
    }

    public void deleteProduct(String productId) {
        if (!productRepository.existsById(productId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado: " + productId);
        }
        productRepository.deleteById(productId);
    }
}
