package com.epam.laboratory.restapipractice.response;

import io.swagger.v3.oas.annotations.media.Schema;

public class RandomResponse {
    @Schema(description = "Название сервиса")
    private final String service;
    @Schema(description = "Случайный факт")
    private final String randomFact;

    public RandomResponse(String service, String randomFact) {
        this.service = service;
        this.randomFact = randomFact;
    }

    public String getService() {
        return service;
    }

    public String getRandomFact() {
        return randomFact;
    }
}
