package com.spring.userservice.service;

import com.spring.userservice.dto.UserDTO;
import com.spring.userservice.repository.UserRepository;
import com.spring.userservice.utility.EntityDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Flux<UserDTO> getAllUsers(){
        return this.userRepository.findAll()
                .map(EntityDtoUtil::toUserDto);
    }

    public Mono<UserDTO> getUserById(final int id){
        return this.userRepository.findById(id)
                .map(EntityDtoUtil::toUserDto);
    }

    public Mono<UserDTO> createUser(Mono<UserDTO> userDTOMono){
        return userDTOMono.map(EntityDtoUtil::toUserEntity)
                .flatMap(this.userRepository::save)
                .map(EntityDtoUtil::toUserDto);
    }

    public Mono<UserDTO> updateUser(int id, Mono<UserDTO> userDTOMono){
        return this.userRepository.findById(id)
                .flatMap(user -> userDTOMono
                        .map(EntityDtoUtil::toUserEntity)
                        .doOnNext(entity -> entity.setId(id)))
                .flatMap(this.userRepository::save)
                .map(EntityDtoUtil::toUserDto);
    }

    public Mono<Void> deleteUser(int id){
        return this.userRepository.deleteById(id);
    }
}
