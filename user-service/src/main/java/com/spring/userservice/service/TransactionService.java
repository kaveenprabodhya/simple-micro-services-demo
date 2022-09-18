package com.spring.userservice.service;

import com.spring.userservice.dto.TransactionRequestDTO;
import com.spring.userservice.dto.TransactionResponseDTO;
import com.spring.userservice.dto.TransactionStatus;
import com.spring.userservice.entity.UserTransaction;
import com.spring.userservice.repository.UserRepository;
import com.spring.userservice.repository.UserTransactionRepository;
import com.spring.userservice.utility.EntityDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransactionService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTransactionRepository transactionRepository;

    public Mono<TransactionResponseDTO> createTransaction(final TransactionRequestDTO requestDTO){
        return this.userRepository
                .updateUserBalance(requestDTO.getUserId(), requestDTO.getAmount())
                .filter(Boolean::booleanValue)
                .map(b -> EntityDtoUtil.toTransactionEntity(requestDTO))
                .flatMap(this.transactionRepository::save)
                .map(userTransaction -> EntityDtoUtil.toTransactionDTO(requestDTO, TransactionStatus.APPROVED))
                .defaultIfEmpty(EntityDtoUtil.toTransactionDTO(requestDTO, TransactionStatus.DECLINED));
    }

    public Flux<UserTransaction> getByUserId(int userId){
        return this.transactionRepository.findByUserId(userId);
    }
}
