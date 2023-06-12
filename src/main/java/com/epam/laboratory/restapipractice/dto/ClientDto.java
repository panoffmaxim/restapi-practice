package com.epam.laboratory.restapipractice.dto;

public class ClientDto {
    private String clientName;
    private String phone;

    public ClientDto(String clientName, String phone) {
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
