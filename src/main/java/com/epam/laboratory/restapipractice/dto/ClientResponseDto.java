package com.epam.laboratory.restapipractice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientResponseDto {
    @Schema(description = "Уникальный идентификатор клиента")
    private Long id;
    @Schema(description = "Имя клиента")
    private String clientName;
    @Schema(description = "Номер телефона клиента")
    private String phone;
}
