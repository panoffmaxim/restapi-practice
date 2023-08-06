package com.epam.laboratory.restapipractice.dto;

public class OrderRequestDto {
    private Long clientId;

    public OrderRequestDto() {
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
}
