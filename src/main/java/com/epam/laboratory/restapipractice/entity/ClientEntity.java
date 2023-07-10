package com.epam.laboratory.restapipractice.entity;

import com.epam.laboratory.restapipractice.customannotations.ClientBean;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "client")
@Schema(description = "Сущность клиента")
@ClientBean
public class ClientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Идентификатор", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "ФИО", example = "Иванов Иван Иванович")
    private String clientName;

    @Schema(description = "Телефон клиента")
    private String phone;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "client")
    @Schema(description = "Список заказов клиента")
    private List<OrderEntity> orders;

    public ClientEntity() {
    }

    public ClientEntity(Long id, String clientName, List<OrderEntity> orders) {
        this.id = id;
        this.clientName = clientName;
        this.orders = orders;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<OrderEntity> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderEntity> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "ClientEntity{" +
                "id=" + id +
                ", clientName='" + clientName + '\'' +
                ", phone='" + phone + '\'' +
                ", orders=" + orders +
                '}';
    }
}
