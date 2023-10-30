package com.epam.laboratory.restapipractice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class ClientResponseDto {
    @Schema(description = "Уникальный идентификатор клиента")
    private Long id;
    @Schema(description = "Имя клиента")
    private String clientName;
    @Schema(description = "Номер телефона клиента")
    private String phone;

    public ClientResponseDto() {
    }

    public ClientResponseDto(Long id, String clientName, String phone) {
        this.id = id;
        this.clientName = clientName;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public String getClientName() {
        return clientName;
    }

    public String getPhone() {
        return phone;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
