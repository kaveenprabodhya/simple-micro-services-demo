package com.spring.userservice.controller;

import com.spring.userservice.dto.UserDTO;
import com.spring.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public Flux<UserDTO> all(){
        return this.userService.getAllUsers();
    }

    @GetMapping("{id}")
    public Mono<ResponseEntity<UserDTO>> getUserById(@PathVariable String id){
        return this.userService
                .getUserById(Integer.parseInt(id))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<UserDTO> createUser(@RequestBody Mono<UserDTO> userDTOMono){
        return this.userService
                .createUser(userDTOMono);
    }

    @PutMapping("{id}")
    public Mono<ResponseEntity<UserDTO>> updateUser(@PathVariable String id, @RequestBody Mono<UserDTO> userDTOMono){
        return this.userService.updateUser(Integer.parseInt(id), userDTOMono)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public Mono<Void> deleteUser(@PathVariable int id){
        return this.userService.deleteUser(id);
    }
}
