package com.epam.laboratory.restapipractice.dto;

public class OrderResponseDto {
    private Long id;
    private String clientName;
    private Long clientId;
    private Boolean completed;
    private String creationDateTime;

    public OrderResponseDto() {
    }

    public OrderResponseDto(Long id, String clientName, Long clientId, Boolean completed, String creationDateTime) {
        this.id = id;
        this.clientName = clientName;
        this.clientId = clientId;
        this.completed = completed;
        this.creationDateTime = creationDateTime;
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

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public String getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(String creationDateTime) {
        this.creationDateTime = creationDateTime;
    }
}
