package com.epam.laboratory.restapipractice.dto;

import java.util.List;

public class OrderListResponseDto {
    private final List<OrderResponseDto> orders;

    public OrderListResponseDto(final List<OrderResponseDto> orders) {
        this.orders = orders;
    }

    public List<OrderResponseDto> getOrders() {
        return orders;
    }
}
