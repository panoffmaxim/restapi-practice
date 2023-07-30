package com.epam.laboratory.restapipractice.dto;

import java.util.List;

public class ClientResponseDto {
    private final Long id;
    private final String clientName;
    private final List<ClientOrderResponse> orders;

    public ClientResponseDto(final Long id, final String clientName, final List<ClientOrderResponse> orders) {
        this.id = id;
        this.clientName = clientName;
        this.orders = orders;
    }

    public Long getId() {
        return id;
    }

    public String getClientName() {
        return clientName;
    }

    public List<ClientOrderResponse> getOrders() {
        return orders;
    }

    public static class ClientOrderResponse {
        private final Long id;
        private final Boolean completed;
        private final String deliveryInf;

        public ClientOrderResponse(final Long id, final Boolean completed, final String deliveryInf) {
            this.id = id;
            this.completed = completed;
            this.deliveryInf = deliveryInf;
        }

        public Long getId() {
            return id;
        }

        public Boolean getCompleted() {
            return completed;
        }

        public String getDeliveryInf() {
            return deliveryInf;
        }
    }
}
