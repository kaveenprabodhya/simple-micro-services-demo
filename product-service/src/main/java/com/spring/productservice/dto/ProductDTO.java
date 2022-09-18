package com.spring.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductDTO {
    private String id;
    private String description;
    private Integer price;

    public ProductDTO(String description, int price) {
        this.description = description;
        this.price = price;
    }
}
