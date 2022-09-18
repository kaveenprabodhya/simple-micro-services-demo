package com.spring.orderservice;

import com.spring.orderservice.client.ProductClient;
import com.spring.orderservice.client.UserClient;
import com.spring.orderservice.dto.ProductDTO;
import com.spring.orderservice.dto.PurchaseOrderRequestDTO;
import com.spring.orderservice.dto.PurchaseOrderResponseDTO;
import com.spring.orderservice.dto.UserDTO;
import com.spring.orderservice.service.OrderFulfilmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
class OrderServiceApplicationTests {

	@Autowired
	private UserClient userClient;

	@Autowired
	private ProductClient productClient;

	@Autowired
	private OrderFulfilmentService fulfilmentService;

	@Test
	void contextLoads() {
		Flux<PurchaseOrderResponseDTO> dtoFlux = Flux.zip(userClient.getAllUsers(), productClient.getAllProducts())
				.map(t -> buildDto(t.getT1(), t.getT2()))
				.flatMap(dto -> this.fulfilmentService.processOrder(Mono.just(dto)))
				.doOnNext(System.out::println);

		StepVerifier.create(dtoFlux)
				.expectNextCount(4)
				.verifyComplete();
	}

	private PurchaseOrderRequestDTO buildDto(UserDTO userDTO, ProductDTO productDTO){
		PurchaseOrderRequestDTO requestDTO = new PurchaseOrderRequestDTO();
		requestDTO.setUserId(userDTO.getId());
		requestDTO.setProductId(productDTO.getId());
		return requestDTO;
	}

}
