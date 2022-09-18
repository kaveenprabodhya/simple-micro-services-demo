package com.spring.orderservice.controller;

import com.spring.orderservice.dto.PurchaseOrderRequestDTO;
import com.spring.orderservice.dto.PurchaseOrderResponseDTO;
import com.spring.orderservice.service.OrderFulfilmentService;
import com.spring.orderservice.service.OrderQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("order")
public class PurchaseOrderController {
    @Autowired
    private OrderFulfilmentService orderFulfilmentService;

    @Autowired
    private OrderQueryService queryService;

    @PostMapping
    public Mono<ResponseEntity<PurchaseOrderResponseDTO>> order(@RequestBody Mono<PurchaseOrderRequestDTO> requestDTOMono){
        return this.orderFulfilmentService.processOrder(requestDTOMono)
                .map(ResponseEntity::ok)
                .onErrorReturn(WebClientResponseException.class, ResponseEntity.badRequest().build())
                .onErrorReturn(WebClientRequestException.class, ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());
    }

    @GetMapping("user/{id}")
    public Flux<PurchaseOrderResponseDTO> getOrdersByUserId(@PathVariable int id){
        return this.queryService.getProductByUserId(id);
    }
}
