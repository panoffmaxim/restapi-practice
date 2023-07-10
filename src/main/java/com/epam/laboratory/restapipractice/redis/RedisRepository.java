package com.epam.laboratory.restapipractice.redis;

import com.epam.laboratory.restapipractice.response.CachedClientListResponse;

public interface RedisRepository {
    void add(CachedClientListResponse cachedClientListResponse);

    CachedClientListResponse findAllClientsFromCache();

    void clearCacheByKey();
}
