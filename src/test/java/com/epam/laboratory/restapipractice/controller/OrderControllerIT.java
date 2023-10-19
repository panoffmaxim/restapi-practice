package com.epam.laboratory.restapipractice.controller;

import com.epam.laboratory.restapipractice.dto.OrderRequestDto;
import com.epam.laboratory.restapipractice.dto.OrderResponseDto;
import com.epam.laboratory.restapipractice.entity.OrderEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureWebTestClient
//@Testcontainers
public class OrderControllerIT extends AbstractTest {
    @Container
    static final MySQLContainer MY_SQL_CONTAINER;

    static {
        MY_SQL_CONTAINER = new MySQLContainer("mysql:latest");
        MY_SQL_CONTAINER.start();
    }

    @Autowired
    private WebTestClient webTestClient;

    @DynamicPropertySource
    static void configureTestProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> MY_SQL_CONTAINER.getJdbcUrl());
        registry.add("spring.datasource.username", () -> MY_SQL_CONTAINER.getUsername());
        registry.add("spring.datasource.password", () -> MY_SQL_CONTAINER.getPassword());
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "update");

    }

    @Test
    void testCreateOrder() {
//        OrderRequestDto orderRequestDto = new OrderRequestDto();
//        orderRequestDto.setClientId(1L);
//        orderRequestDto.setDeliveryInf("order-2");
//        orderRequestDto.setPaymentMethod("cash");
        webTestClient.post()
                .uri("/orders/create")
                .bodyValue(orderOne)
                .exchange()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectStatus().isOk()
                .expectBody(OrderEntity.class)
                .consumeWith(order -> Assertions.assertNotNull(order.getResponseBody().getDeliveryInf()));
//        .andExpect()???
    }

    @Test
    void testGetAllOrders() {
        webTestClient.get()
                .uri("/orders/all")
                .exchange()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectStatus().isOk()
                .expectBodyList(OrderResponseDto.class)
                .consumeWith(listOfObject -> {
                    var list = listOfObject.getResponseBody();
                    Assertions.assertEquals(2, list.size());
                });
    }
}
