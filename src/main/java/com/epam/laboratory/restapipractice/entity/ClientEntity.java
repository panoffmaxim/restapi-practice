package com.epam.laboratory.restapipractice.entity;

import com.epam.laboratory.restapipractice.customannotations.ClientBean;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "client")
@Schema(description = "Сущность клиента")
@ClientBean
@RedisHash("Client")
public class ClientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Идентификатор", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "ФИО", example = "Иванов Иван Иванович")
    @Column(name = "clientName")
    @Indexed private String clientName;

    @Schema(description = "Телефон клиента")
    @Column(name = "phone")
    private String phone;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "client")
    @Schema(description = "Список заказов клиента")
    @Column(name = "orders")
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
}
