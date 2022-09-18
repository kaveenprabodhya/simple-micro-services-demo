package com.spring.userservice.controller;

import com.spring.userservice.dto.TransactionRequestDTO;
import com.spring.userservice.dto.TransactionResponseDTO;
import com.spring.userservice.entity.UserTransaction;
import com.spring.userservice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("user/transaction")
public class UserTransactionController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public Mono<TransactionResponseDTO> createTransaction(@RequestBody Mono<TransactionRequestDTO> requestDTOMono){
        return requestDTOMono.flatMap(this.transactionService::createTransaction);
    }

    @GetMapping
    public Flux<UserTransaction> getByUserId(@RequestParam("userId") int id){
        return this.transactionService.getByUserId(id);
    }
}
