package com.epam.laboratory.restapipractice.dto;

public class OrderResponseDto {
    private Long id;
    private Long clientId;
    private Boolean completed;
    private String deliveryInf;
    private String paymentMethod;
    private String creationDateTime;

    public OrderResponseDto() {
    }

    public OrderResponseDto(Long id, Long clientId, Boolean completed, String deliveryInf, String paymentMethod, String creationDateTime) {
        this.id = id;
        this.clientId = clientId;
        this.completed = completed;
        this.deliveryInf = deliveryInf;
        this.paymentMethod = paymentMethod;
        this.creationDateTime = creationDateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public String getDeliveryInf() {
        return deliveryInf;
    }

    public void setDeliveryInf(String deliveryInf) {
        this.deliveryInf = deliveryInf;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(String creationDateTime) {
        this.creationDateTime = creationDateTime;
    }
}
