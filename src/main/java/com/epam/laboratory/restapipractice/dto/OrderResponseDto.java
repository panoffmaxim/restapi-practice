package com.epam.laboratory.restapipractice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {
    private Long id;
    private Long clientId;
    private Boolean completed;
    private String deliveryInf;
    private String paymentMethod;
    private String creationDateTime;
}
