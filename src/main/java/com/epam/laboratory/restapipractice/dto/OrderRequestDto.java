package com.epam.laboratory.restapipractice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {
    private Long clientId;
    private String deliveryInf;
    private String paymentMethod;
}
