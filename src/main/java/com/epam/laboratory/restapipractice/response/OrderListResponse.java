package com.epam.laboratory.restapipractice.response;

import java.util.List;

public class OrderListResponse {
    private final List<OrderResponse> orders;

    public OrderListResponse(final List<OrderResponse> orders) {
        this.orders = orders;
    }

    public List<OrderResponse> getOrders() {
        return orders;
    }
}
