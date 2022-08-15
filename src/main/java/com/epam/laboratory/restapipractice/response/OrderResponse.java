package com.epam.laboratory.restapipractice.response;

public class OrderResponse {
    private final Long id;
    private final String clientName;
    private final Boolean completed;
    private final String deliveryInf;
    private final Long ClientId;

    public OrderResponse(final Long id, final String clientName, final Boolean completed, final String deliveryInf, Long ClientId) {
        this.id = id;
        this.clientName = clientName;
        this.completed = completed;
        this.deliveryInf = deliveryInf;
        this.ClientId = ClientId;
    }

    public Long getId() {
        return id;
    }

    public String getClientName() {
        return clientName;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public String getDeliveryInf() {
        return deliveryInf;
    }

    public Long getClientId() {
        return ClientId;
    }
}
