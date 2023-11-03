package com.epam.laboratory.restapipractice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientRequestDto {
    @Schema(description = "Имя клиента", example = "Иван")
    private String clientName;
    @Schema(description = "Телефон клиента", example = "+7-700-111-11-11")
    private String phone;
}
