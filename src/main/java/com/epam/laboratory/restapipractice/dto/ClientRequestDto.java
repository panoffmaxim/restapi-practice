package com.epam.laboratory.restapipractice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class ClientRequestDto {
    @Schema(description = "Имя клиента", example = "Иван")
    private String clientName;
    @Schema(description = "Телефон клиента", example = "+7-700-111-11-11")
    private String phone;

    public ClientRequestDto() {
    }

    public ClientRequestDto(String clientName, String phone) {
        this.clientName = clientName;
        this.phone = phone;
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
}
