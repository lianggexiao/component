package com.qing.simpleframe.repository;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import com.qing.simpleframe.domain.entity.User;

@Transactional
//@CacheConfig(cacheNames = "users")
public interface DemoRepository {

//    @Cacheable(key = "#p0")
    User findByName(String name);
    
//    @CachePut(key = "#p0.name")
    void insertOne(User user);
}
