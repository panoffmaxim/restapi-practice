package com.epam.laboratory.restapipractice.response;

import java.util.List;

public class CachedClientResponse {
    private final Long id;
    private final String clientName;
    private final List<CachedClientOrderResponse> orders;

    public CachedClientResponse(final Long id, final String clientName, final List<CachedClientOrderResponse> orders) {
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

    public List<CachedClientOrderResponse> getOrders() {
        return orders;
    }

    public static class CachedClientOrderResponse {
        private final Long id;
        private final Boolean completed;
        private final String deliveryInf;

        public CachedClientOrderResponse(final Long id, final Boolean completed, final String deliveryInf) {
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
