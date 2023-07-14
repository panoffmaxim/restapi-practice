package com.epam.laboratory.restapipractice.dto;

import java.time.LocalDateTime;

public class OrderResponseDto {
    private Long id;
    private String clientName;
    private Boolean completed;
    private LocalDateTime creationDateTime;

    public OrderResponseDto() {
    }

    public OrderResponseDto(Long id, String clientName, Boolean completed) {
        this.id = id;
        this.clientName = clientName;
        this.completed = completed;
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

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }
}
