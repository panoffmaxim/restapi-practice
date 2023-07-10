package com.epam.laboratory.restapipractice.dto;

public class ClientResponseDto {
    private Long id;
    private String clientName;
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
}
