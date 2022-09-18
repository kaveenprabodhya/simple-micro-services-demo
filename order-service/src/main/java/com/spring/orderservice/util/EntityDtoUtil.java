package com.spring.orderservice.util;

import com.spring.orderservice.dto.*;
import com.spring.orderservice.entity.PurchaseOrder;
import com.spring.orderservice.dto.TransactionRequestDTO;
import com.spring.orderservice.dto.TransactionStatus;
import org.springframework.beans.BeanUtils;

public class EntityDtoUtil {

    public static PurchaseOrderResponseDTO getPurchaseOrderResponseDTO(PurchaseOrder purchaseOrder){
        PurchaseOrderResponseDTO responseDTO = new PurchaseOrderResponseDTO();
        BeanUtils.copyProperties(purchaseOrder, responseDTO);
        responseDTO.setOrderId(purchaseOrder.getId());
        return responseDTO;
    }

    public static void setTransactionRequestDTO(RequestContext requestContext){
        TransactionRequestDTO dto = new TransactionRequestDTO();
        dto.setAmount(requestContext.getProductDTO().getPrice());
        dto.setUserId(requestContext.getPurchaseOrderRequestDTO().getUserId());
        requestContext.setTransactionRequestDTO(dto);
    }

    public static PurchaseOrder getPurchaseOrder(RequestContext requestContext){
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setUserId(requestContext.getPurchaseOrderRequestDTO().getUserId());
        purchaseOrder.setProductId(requestContext.getPurchaseOrderRequestDTO().getProductId());
        purchaseOrder.setAmount(requestContext.getProductDTO().getPrice());

        TransactionStatus status = requestContext.getTransactionResponseDTO().getStatus();
        OrderStatus orderStatus = TransactionStatus.APPROVED.equals(status) ?
                OrderStatus.COMPLETED :
                OrderStatus.FAILED;

        purchaseOrder.setStatus(orderStatus);
        return purchaseOrder;
    }
}
