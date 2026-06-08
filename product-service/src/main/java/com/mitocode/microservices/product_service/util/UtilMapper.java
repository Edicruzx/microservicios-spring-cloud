package com.mitocode.microservices.product_service.util;

import com.mitocode.microservices.product_service.model.dto.ProductDTO;
import com.mitocode.microservices.product_service.model.entity.ProductEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;


@Component
public class UtilMapper {

    public ProductEntity convertDTOToEntity(ProductDTO productDTO) {
        ProductEntity productEntity = ProductEntity.builder().build();
        BeanUtils.copyProperties(productDTO, productEntity);
        return productEntity;
    }


    public ProductDTO convertEntityToDTO(ProductEntity productEntity) {
        ProductDTO productDTO = ProductDTO.builder().build();
        BeanUtils.copyProperties(productEntity, productDTO);
        return productDTO;
    }


}