package com.epam.laboratory.restapipractice.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean completed;
    private String deliveryInf;
    private String paymentMethod;
    private LocalDateTime creationDateTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "clientId", referencedColumnName = "id")
    private ClientEntity client;

    public OrderEntity() {
    }

    public OrderEntity(Long id, Boolean completed, String deliveryInf, String paymentMethod, LocalDateTime creationDateTime, ClientEntity client) {
        this.id = id;
        this.completed = completed;
        this.deliveryInf = deliveryInf;
        this.paymentMethod = paymentMethod;
        this.creationDateTime = creationDateTime;
        this.client = client;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public ClientEntity getClient() {
        return client;
    }

    public void setClient(ClientEntity client) {
        this.client = client;
    }
}
