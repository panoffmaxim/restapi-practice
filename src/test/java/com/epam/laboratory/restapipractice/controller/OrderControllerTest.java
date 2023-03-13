package com.epam.laboratory.restapipractice.controller;

import com.epam.laboratory.restapipractice.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    OrderController orderController;

    @Test
    void shouldReturnNotFoundIfOrderServiceReturnNull() {
//        OrderController orderController = new OrderController(orderService);
        Mockito.when(orderService.getAllOrders()).thenReturn(null);
        var response = orderController.getAllOrders();
        Mockito.verify(orderService, Mockito.times(1)).getAllOrders();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
