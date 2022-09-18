package com.spring.orderservice.client;

import com.spring.orderservice.dto.TransactionRequestDTO;
import com.spring.orderservice.dto.TransactionResponseDTO;
import com.spring.orderservice.dto.UserDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserClient {
    private final WebClient webClient;

    public UserClient(@Value("${user.service.url}") String url){
        this.webClient = WebClient.builder().baseUrl(url).build();
    }

    public Mono<TransactionResponseDTO> authorizeTransaction(TransactionRequestDTO requestDTO){
        return this.webClient
                .post()
                .uri("transaction")
                .bodyValue(requestDTO)
                .retrieve()
                .bodyToMono(TransactionResponseDTO.class);
    }

    public Flux<UserDTO> getAllUsers(){
        return this.webClient
                .get()
                .retrieve()
                .bodyToFlux(UserDTO.class);
    }
}
