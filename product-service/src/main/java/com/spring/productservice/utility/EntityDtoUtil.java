package com.spring.productservice.utility;

import com.spring.productservice.dto.ProductDTO;
import com.spring.productservice.entity.Product;
import org.springframework.beans.BeanUtils;

public class EntityDtoUtil {
    public static ProductDTO toDto(Product product) {
        ProductDTO dto = new ProductDTO();
//        dto.setDescription(product.getDescription());
        BeanUtils.copyProperties(product, dto);
        return dto;
    }

    public static Product toEntity(ProductDTO productDTO) {
        Product product = new Product();
        BeanUtils.copyProperties(productDTO, product);
        return product;
    }
}
