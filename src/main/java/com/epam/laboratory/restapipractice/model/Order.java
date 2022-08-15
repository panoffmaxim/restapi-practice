package com.epam.laboratory.restapipractice.model;

import com.epam.laboratory.restapipractice.entity.OrderEntity;

public class Order {
    private Long id;
    private String clientName;
    private Boolean completed;
    private String deliveryInf;

    public static Order toModel(OrderEntity entity) {
        Order model = new Order();
        model.setId(entity.getId());
        model.setCompleted(entity.getCompleted());
        model.setClientName(entity.getClientName());
        model.setDeliveryInf(entity.getDeliveryInf());
        return model;
    }

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getDeliveryInf() {
        return deliveryInf;
    }

    public void setDeliveryInf(String deliveryInf) {
        this.deliveryInf = deliveryInf;
    }
}
