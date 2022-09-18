package com.spring.orderservice.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PurchaseOrderResponseDTO {
    private Integer OrderId;
    private Integer userId;
    private String productId;
    private Integer amount;
    private OrderStatus status;
}
