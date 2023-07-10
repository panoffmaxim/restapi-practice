package com.epam.laboratory.restapipractice.dto;

public class OrderRequestDto {
    private String clientName;

    public OrderRequestDto() {
    }

    public OrderRequestDto(String clientName) {
        this.clientName = clientName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
}
