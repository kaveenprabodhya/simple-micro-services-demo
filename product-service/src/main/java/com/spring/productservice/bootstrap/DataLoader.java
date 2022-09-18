package com.spring.productservice.bootstrap;

import com.spring.productservice.dto.ProductDTO;
import com.spring.productservice.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    private ProductService productService;

    @Override
    public void run(String... args) throws Exception {
//        if(this.productService.getAll().count().block() == 0){
            ProductDTO p1 = new ProductDTO("4k-TV", 1000);
            ProductDTO p2 = new ProductDTO("iphone", 1499);
            ProductDTO p3 = new ProductDTO("tablet", 900);
            ProductDTO p4 = new ProductDTO("lens", 300);

            Flux.just(p1,p2,p3,p4)
                    .concatWith(newProducts())
                    .flatMap(p -> this.productService.insertProduct(Mono.just(p)))
                    .subscribe(System.out::println);
//        }
    }

    private Flux<ProductDTO> newProducts(){
        return Flux.range(1,1000)
                .delayElements(Duration.ofSeconds(2))
                .map(i -> new ProductDTO("product-" +i, ThreadLocalRandom.current().nextInt(10,100)));
    }
}
