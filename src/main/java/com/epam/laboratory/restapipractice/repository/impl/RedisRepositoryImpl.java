package com.epam.laboratory.restapipractice.repository.impl;

import com.epam.laboratory.restapipractice.entity.ClientEntity;
import com.epam.laboratory.restapipractice.repository.RedisRepository;
import com.epam.laboratory.restapipractice.response.CachedClientListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Map;

@Repository
public class RedisRepositoryImpl implements RedisRepository {
    private static final String KEY = "Client";
    private RedisTemplate<String, CachedClientListResponse> redisTemplate;
    private HashOperations hashOperations;
    @Autowired
    public RedisRepositoryImpl(RedisTemplate<String, CachedClientListResponse> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    @PostConstruct
    private void init() {
        hashOperations = redisTemplate.opsForHash();
    }
    public void add(final ClientEntity clientEntity) {
        hashOperations.put(KEY, clientEntity.getId(), clientEntity.getClientName());
    }
    public Map<Object, Object> findAllClientsFromCache() {
        return hashOperations.entries(KEY);
    }
}
