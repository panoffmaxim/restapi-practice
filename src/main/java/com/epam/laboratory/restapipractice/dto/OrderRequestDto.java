package com.epam.laboratory.restapipractice.dto;

public class OrderRequestDto {
    private String clientName;
    private Boolean completed;

    public OrderRequestDto() {
    }

    public OrderRequestDto(String clientName, Boolean completed) {
        this.clientName = clientName;
        this.completed = completed;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}
