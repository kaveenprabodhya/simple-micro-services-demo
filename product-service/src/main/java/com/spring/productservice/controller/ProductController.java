package com.spring.productservice.controller;

import com.spring.productservice.dto.ProductDTO;
import com.spring.productservice.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public Flux<ProductDTO> all(){
        return this.productService.getAll();
    }

    @GetMapping("price-range")
    public Flux<ProductDTO> getByPriceRange(@RequestParam("min") int min, @RequestParam("max") int max){
        return this.productService.getProductByPriceRange(min,max);
    }

    @GetMapping("{id}")
    public Mono<ResponseEntity<ProductDTO>> getProductById(@PathVariable String id){
        return this.productService.getProductById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @PostMapping
    public Mono<ProductDTO> insertProduct(@RequestBody Mono<ProductDTO> productDTOMono){
        return this.productService
                .insertProduct(productDTOMono);
    }

    @PutMapping("{id}")
    public Mono<ResponseEntity<ProductDTO>> updateProduct(@PathVariable String id, @RequestBody Mono<ProductDTO> productDTOMono){
        return this.productService.updateProduct(id, productDTOMono)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public Mono<Void> deleteProduct(@PathVariable String id){
        return this.productService.deleteProduct(id);
    }

    private void simulateRandomException(){
        int nextInt = ThreadLocalRandom.current().nextInt(1,10);
        if(nextInt > 5)
            throw new RuntimeException("Something went wrong. please try again later.");
    }
}
