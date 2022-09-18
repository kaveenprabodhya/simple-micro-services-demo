package com.spring.orderservice.service;

import com.spring.orderservice.client.ProductClient;
import com.spring.orderservice.client.UserClient;
import com.spring.orderservice.dto.PurchaseOrderRequestDTO;
import com.spring.orderservice.dto.PurchaseOrderResponseDTO;
import com.spring.orderservice.dto.RequestContext;
import com.spring.orderservice.repositories.PurchaseOrderRepository;
import com.spring.orderservice.util.EntityDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;

import java.time.Duration;

@Service
public class OrderFulfilmentService {
    @Autowired
    private ProductClient productClient;
    @Autowired
    private UserClient userClient;
    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    public Mono<PurchaseOrderResponseDTO> processOrder(Mono<PurchaseOrderRequestDTO> requestDTOMono){
        return requestDTOMono.map(RequestContext::new)
                .flatMap(this::productRequestResponse)
                .doOnNext(EntityDtoUtil::setTransactionRequestDTO)
                .flatMap(this::userRequestResponse)
                .map(EntityDtoUtil::getPurchaseOrder)
                .map(this.purchaseOrderRepository::save)
                .map(EntityDtoUtil::getPurchaseOrderResponseDTO)
                .subscribeOn(Schedulers.boundedElastic());
    }

    private Mono<RequestContext> productRequestResponse(RequestContext requestContext){
        return this.productClient
                .getProductById(requestContext.getPurchaseOrderRequestDTO().getProductId())
                .doOnNext(requestContext::setProductDTO)
                .retryWhen(Retry.fixedDelay(5, Duration.ofSeconds(1)))
                .thenReturn(requestContext);
    }

    private Mono<RequestContext> userRequestResponse(RequestContext requestContext){
        return this.userClient.authorizeTransaction(requestContext.getTransactionRequestDTO())
                .doOnNext(requestContext::setTransactionResponseDTO)
                .thenReturn(requestContext);
    }
}
