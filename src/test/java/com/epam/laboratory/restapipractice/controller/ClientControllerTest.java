package com.epam.laboratory.restapipractice.controller;

import com.epam.laboratory.restapipractice.entity.ClientEntity;
import com.epam.laboratory.restapipractice.entity.OrderEntity;
import com.epam.laboratory.restapipractice.model.Client;
import com.epam.laboratory.restapipractice.service.ClientService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClientControllerTest {

    private final ClientService clientService = mock(ClientService.class);

    private final String apiUrl = "http://test.tt";

    private final ClientController clientController = new ClientController(clientService, apiUrl);

    @Test
    void getClientOrders_returnsNotFound() {
        //given
        var clientId = 1L;
        when(clientService.findClientOrders(clientId)).thenReturn(null);

        //when
        var response = clientController.getClientOrders(clientId);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void readClientById_ShouldReturnClientAndOkStatus() {
        ClientEntity client = new ClientEntity();
        OrderEntity orderOne = new OrderEntity();
        OrderEntity orderTwo = new OrderEntity();

        orderOne.setCompleted(true);
        orderOne.setClientName("josh");
        orderOne.setPaymentMethod("credit-card");
        orderOne.setDeliveryInf("today");
        orderTwo.setCompleted(true);
        orderTwo.setClientName("dan");
        orderTwo.setPaymentMethod("cash");
        orderTwo.setDeliveryInf("tomorrow");
        List<OrderEntity> orders = new ArrayList<>();
        orders.add(orderOne);
        orders.add(orderTwo);
        client.setId(1L);
        client.setClientName("Ginger");
        client.setPhone("777");
        client.setOrders(orders);

        when(clientService.read(anyLong())).thenReturn(Client.toModel(client));
        ResponseEntity<Client> clientEntity = clientController.read(1L);
        assertNotNull(clientEntity);
        assertThat(clientEntity.getStatusCodeValue()).isEqualTo(200);
    }
}