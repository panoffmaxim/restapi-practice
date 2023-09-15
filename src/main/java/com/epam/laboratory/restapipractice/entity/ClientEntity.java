package com.epam.laboratory.restapipractice.entity;

import com.epam.laboratory.restapipractice.customannotations.ClientBean;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;

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

    public ClientEntity() {
    }

    public ClientEntity(Long id, String clientName, String phone) {
        this.id = id;
        this.clientName = clientName;
        this.phone = phone;
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

    @Override
    public String toString() {
        return "ClientEntity{" +
                "id=" + id +
                ", clientName='" + clientName + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
