package com.epam.laboratory.restapipractice.repository;

import com.epam.laboratory.restapipractice.entity.ClientEntity;

import java.util.Map;

public interface RedisRepository {
    void add(ClientEntity clientEntity);
    Map<Object, Object> findAllClientsFromCache();
}
