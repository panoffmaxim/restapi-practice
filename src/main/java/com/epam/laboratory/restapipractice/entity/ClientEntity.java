package com.epam.laboratory.restapipractice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "client")
@Schema(description = "Сущность клиента")
@JsonIgnoreProperties(value = {"roles"})
public class ClientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Идентификатор", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "ФИО", example = "Иванов Иван Иванович")
    @Column(name = "clientName")
    private String clientName;

    @Schema(description = "Телефон клиента")
    @Column(name = "phone")
    private String phone;

    @Schema(description = "Пароль клиента")
    @Column(name = "password")
    private String password;

    @Schema(description = "Статус аккаунта")
    @Column(name = "enabled")
    private boolean enabled;

    /*@OneToMany(cascade = CascadeType.ALL, mappedBy = "client")
    @Schema(description = "Список заказов клиента")
    @Column(name = "orders")
    private List<OrderEntity> orders;*/

    @ManyToMany
    @JoinTable(
            name = "client_roles",
            joinColumns = @JoinColumn(
                    name = "client_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<RoleEntity> roleEntities;

    public ClientEntity() {
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<RoleEntity> getRoles() {
        return roleEntities;
    }

    public void setRoles(Collection<RoleEntity> roleEntities) {
        this.roleEntities = roleEntities;
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

    /*public List<OrderEntity> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderEntity> orders) {
        this.orders = orders;
    }*/
}
