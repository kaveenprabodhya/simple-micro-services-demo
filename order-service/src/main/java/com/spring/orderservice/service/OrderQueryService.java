package com.spring.orderservice.service;

import com.spring.orderservice.dto.PurchaseOrderResponseDTO;
import com.spring.orderservice.repositories.PurchaseOrderRepository;
import com.spring.orderservice.util.EntityDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;


@Service
public class OrderQueryService {
    @Autowired
    private PurchaseOrderRepository orderRepository;

    public Flux<PurchaseOrderResponseDTO> getProductByUserId(int userId){
        return Flux.fromStream(() ->this.orderRepository.findByUserId(userId).stream())
                .map(EntityDtoUtil::getPurchaseOrderResponseDTO)
                .subscribeOn(Schedulers.boundedElastic());
    }
}
