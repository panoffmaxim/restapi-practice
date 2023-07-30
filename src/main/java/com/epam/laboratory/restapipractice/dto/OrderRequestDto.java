package com.epam.laboratory.restapipractice.dto;

public class OrderRequestDto {
    private String clientName;
    private Long clientId;

    public OrderRequestDto() {
    }

    public OrderRequestDto(String clientName, Long clientId) {
        this.clientName = clientName;
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
}
