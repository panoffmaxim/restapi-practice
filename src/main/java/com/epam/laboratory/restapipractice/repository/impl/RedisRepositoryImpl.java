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
    private static final String KEY = "clientList";
    private final RedisTemplate<String, CachedClientListResponse> redisTemplate;

    @Autowired
    public RedisRepositoryImpl(RedisTemplate<String, CachedClientListResponse> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void add(final CachedClientListResponse cachedClientListResponse) {
        redisTemplate.opsForValue().set(KEY, cachedClientListResponse);
    }

    public CachedClientListResponse findAllClientsFromCache() {
        return redisTemplate.opsForValue().get(KEY);
    }

    public void clearCacheByKey() {
        redisTemplate.opsForValue().getAndDelete(KEY);
    }
}
