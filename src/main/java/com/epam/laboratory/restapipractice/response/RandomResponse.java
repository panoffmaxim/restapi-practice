package com.epam.laboratory.restapipractice.response;

public class RandomResponse {
    private final String service;
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
