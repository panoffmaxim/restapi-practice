package com.epam.laboratory.restapipractice.model;

import com.epam.laboratory.restapipractice.entity.ClientEntity;

import java.util.List;
import java.util.stream.Collectors;

public class Client {

    private Long id;
    private String clientName;
    private List<Order> orders;

    public static Client toModel(ClientEntity entity) {
        Client model = new Client();
        model.setId(entity.getId());
        model.setClientName(entity.getClientName());
        model.setOrders(entity.getOrders().stream().map(Order::toModel).collect(Collectors.toList()));
        return model;
    }

    public Client() {
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

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
