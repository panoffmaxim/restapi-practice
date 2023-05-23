package com.epam.laboratory.restapipractice.redis;

import com.epam.laboratory.restapipractice.entity.ClientEntity;
import com.epam.laboratory.restapipractice.response.CachedClientListResponse;

import java.util.Map;

public interface RedisRepository {
    void add(CachedClientListResponse cachedClientListResponse);
    CachedClientListResponse findAllClientsFromCache();

    CachedClientListResponse clearCacheByKey();
}
