//package com.epam.laboratory.restapipractice.controller;
//
//import com.epam.laboratory.restapipractice.entity.ClientEntity;
//import com.epam.laboratory.restapipractice.entity.OrderEntity;
//import com.fasterxml.jackson.core.JsonParseException;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonMappingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.jdbc.JdbcTestUtils;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//@WebAppConfiguration
//public abstract class AbstractTest {
//    protected MockMvc mvc;
//    @Autowired
//    WebApplicationContext webApplicationContext;
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    ClientEntity client;
//    OrderEntity orderOne;
//    OrderEntity orderTwo;
//
//    @BeforeEach
//    protected void setUp() {
//        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
//
//        client = new ClientEntity();
//        orderOne = new OrderEntity();
//        orderTwo = new OrderEntity();
//
//        orderOne.setCompleted(true);
//        orderOne.setClientName("josh");
//        orderOne.setPaymentMethod("credit-card");
//        orderOne.setDeliveryInf("today");
//        orderTwo.setCompleted(true);
//        orderTwo.setClientName("dan");
//        orderTwo.setPaymentMethod("cash");
//        orderTwo.setDeliveryInf("tomorrow");
//        List<OrderEntity> orders = new ArrayList<>();
//        orders.add(orderOne);
//        orders.add(orderTwo);
//
//        client.setClientName("Ginger");
//        client.setPhone("777");
//        client.setOrders(orders);
//    }
//
//    @AfterEach
//    protected void tearDown() {
//        JdbcTestUtils.deleteFromTables(jdbcTemplate, "client", "orders");
//    }
//
//
//    protected String mapToJson(Object obj) throws JsonProcessingException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        return objectMapper.writeValueAsString(obj);
//    }
//
//    protected <T> T mapFromJson(String json, Class<T> clazz)
//            throws JsonParseException, JsonMappingException, IOException {
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        return objectMapper.readValue(json, clazz);
//    }
//}
