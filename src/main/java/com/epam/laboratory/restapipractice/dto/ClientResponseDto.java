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

    public String getClientName() {
        return clientName;
    }

    public String getPhone() {
        return phone;
    }
}
