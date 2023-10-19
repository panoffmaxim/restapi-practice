package com.epam.laboratory.restapipractice.controller;

import com.epam.laboratory.restapipractice.dto.OrderRequestDto;
import com.epam.laboratory.restapipractice.entity.ClientEntity;
import com.epam.laboratory.restapipractice.entity.OrderEntity;
import com.epam.laboratory.restapipractice.repository.OrderRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@Testcontainers
public class AbstractTest {

    @Autowired
    private OrderRepo orderRepository;
    ClientEntity clientOne;
    ClientEntity clientTwo;
    OrderEntity orderOne;
    OrderEntity orderTwo;

    @BeforeEach
    void setUp() {
        clientOne = new ClientEntity();
        clientOne.setId(1L);

        orderOne = new OrderEntity();
        orderOne.setId(1L);
//        orderOne.setClientId(1L);
        orderOne.setClient(clientOne);
        orderOne.setCompleted(true);
        orderOne.setDeliveryInf("order1");
        orderOne.setPaymentMethod("credit card");
        orderOne.setCreationDateTime(LocalDateTime.now());
        orderRepository.save(orderOne);

        orderTwo = new OrderEntity();
        orderTwo.setId(2L);
        orderTwo.setCompleted(true);
        orderTwo.setDeliveryInf("order2");
        orderTwo.setPaymentMethod("cash");
        orderTwo.setCreationDateTime(LocalDateTime.now());
        orderRepository.save(orderTwo);
    }

    @AfterEach
    void tearDown() {
        orderRepository.deleteAll();
    }
}
