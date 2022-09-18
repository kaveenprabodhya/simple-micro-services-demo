package com.spring.orderservice.entity;

import com.spring.orderservice.dto.OrderStatus;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@ToString
public class PurchaseOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String productId;
    private Integer userId;
    private Integer amount;
    private OrderStatus status;
}
