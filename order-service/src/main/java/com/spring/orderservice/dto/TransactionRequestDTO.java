package com.spring.orderservice.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TransactionRequestDTO {
    private Integer userId;
    private Integer amount;
}
