package com.epam.laboratory.restapipractice.controller;

import com.epam.laboratory.restapipractice.entity.ClientEntity;
import com.epam.laboratory.restapipractice.entity.OrderEntity;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
class ClientControllerTest extends AbstractTest {
    @Override
    @Before("create")
    public void setUp() {
        super.setUp();
    }
//    @Autowired
//    private ClientController clientController;
//    @MockBean
//    private ClientService clientService;

    @Test
    public void create() throws Exception {
        ClientEntity client = new ClientEntity();
//        clientController.create(client);
        OrderEntity orderOne = new OrderEntity();
        orderOne.setId(2L);
        orderOne.setCompleted(true);
        orderOne.setClientName("josh");
        orderOne.setPaymentMethod("credit-card");
        OrderEntity orderTwo = new OrderEntity();
        orderTwo.setId(3L);
        orderTwo.setCompleted(true);
        orderTwo.setClientName("dan");
        orderTwo.setPaymentMethod("cash");
        List<OrderEntity> orders = new ArrayList<>();
        orders.add(orderOne);
        orders.add(orderTwo);

        String uri = "/clients";
        client.setId(1L);
        client.setClientName("Ginger");
        client.setPhone("777");
        client.setOrders(orders);
        String inputJson = super.mapToJson(client);
        MvcResult mvcResult = mvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);

//        mockMvc.perform(post("/clients")
//                        .contentType(AbstractTest.APPLICATION_JSON_UTF8)
//                        .content(AbstractTest.convertObjectToJsonBytes(dto))
//                )
//                .andExpect(status().isCreated())
//                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
//                .andExpect(jsonPath("$.id", is(1)))
//                .andExpect(jsonPath("$.clientName", is("description")))
//                .andExpect(jsonPath("$.phone", is("title")));
    }
}