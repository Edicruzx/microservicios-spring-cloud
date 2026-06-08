package com.mitocode.microservices.product_service.service;
import com.mitocode.microservices.product_service.model.dto.ProductDTO;
import com.mitocode.microservices.product_service.model.entity.ProductEntity;
import com.mitocode.microservices.product_service.service.repository.ProductRepository;
import com.mitocode.microservices.product_service.util.UtilMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
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

        TimeUnit.MILLISECONDS.sleep(10L);

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
}