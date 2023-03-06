package com.epam.laboratory.restapipractice.controller;

import com.epam.laboratory.restapipractice.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderContollerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    OrderController orderController;

    @Test
    void shouldReturnNotFoundIfOrderServiceReturnNull() {
//        OrderController orderController = new OrderController(orderService);
        when(orderService.getAllOrders()).thenReturn(null);
        var response = orderController.getAllOrders();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
