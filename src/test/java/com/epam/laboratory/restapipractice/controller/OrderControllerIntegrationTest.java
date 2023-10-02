package com.epam.laboratory.restapipractice.controller;

import com.epam.laboratory.restapipractice.dto.OrderRequestDto;
import com.epam.laboratory.restapipractice.dto.OrderResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@Testcontainers
public class OrderControllerIntegrationTest {
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
        OrderRequestDto orderRequestDto = new OrderRequestDto();
        orderRequestDto.setClientId(1L);
        orderRequestDto.setDeliveryInf("order-2");
        orderRequestDto.setPaymentMethod("cash");
        webTestClient.post()
                .uri("/orders/create")
                .bodyValue(orderRequestDto)
                .exchange()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectStatus().isOk()
                .expectBody(OrderRequestDto.class)
                .consumeWith(order -> Assertions.assertNotNull(order.getResponseBody().getDeliveryInf()));
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
