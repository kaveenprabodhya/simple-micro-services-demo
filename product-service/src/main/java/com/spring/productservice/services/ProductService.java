package com.spring.productservice.services;

import com.spring.productservice.dto.ProductDTO;
import com.spring.productservice.repositories.ProductRepository;
import com.spring.productservice.utility.EntityDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private Sinks.Many<ProductDTO> sink;

    public Flux<ProductDTO> getAll(){
        return this.productRepository.findAll()
                .map(EntityDtoUtil::toDto);
    }

    public Flux<ProductDTO> getProductByPriceRange(int min, int max){
        return this.productRepository.findByPriceBetween(Range.closed(min, max))
                .map(EntityDtoUtil::toDto);
    }

    public Mono<ProductDTO> getProductById(String id){
        return this.productRepository.findById(id).map(EntityDtoUtil::toDto);
    }

    public  Mono<ProductDTO> insertProduct(Mono<ProductDTO> productDTOMono){
        return productDTOMono.map(EntityDtoUtil::toEntity)
                .flatMap(this.productRepository::insert)
                .map(EntityDtoUtil::toDto)
                .doOnNext(this.sink::tryEmitNext);
    }

    public Mono<ProductDTO> updateProduct(String id, Mono<ProductDTO> productDTOMono){
        return this.productRepository.findById(id)
                .flatMap(product -> productDTOMono
                        .map(EntityDtoUtil::toEntity)
                        .doOnNext(entity -> entity.setId(id)))
                .flatMap(this.productRepository::save)
                .map(EntityDtoUtil::toDto);
    }

    public Mono<Void> deleteProduct(String id){
        return this.productRepository.deleteById(id);
    }
}
