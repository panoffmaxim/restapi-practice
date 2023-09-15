package com.epam.laboratory.restapipractice.dto;

public class OrderRequestDto {
    private Long clientId;
    private String deliveryInf;
    private String paymentMethod;

    public OrderRequestDto() {
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
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
}
