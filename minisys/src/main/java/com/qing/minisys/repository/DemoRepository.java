package com.qing.minisys.repository;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import com.qing.minisys.domain.entity.User;

import java.util.List;

/**
 *  这里主要演示了Spring缓存的使用，@CachePut和@Cacheable这两个标签可以结合使用，当需要根据请求改变值的时候，
 *  利用@CachePut将值改变并写入到缓存中，而@Cacheable标签除了第一次之外，一直是取的缓存的值。
 *  结合使用时需要注意两点：
 *  1、必须是同一个缓存实例。
 *  2、key值必须是相同的。
 */
@Transactional
@CacheConfig(cacheNames = "users")
public interface DemoRepository {

    /**
     * 注意@Cacheable：当重复使用相同参数调用方法的时候，方法本身不会被调用执行，即方法本身被略过了，取而代之的是方法的结果直接从缓存中找到并返回了。
     * @param name
     * @return
     */
    @Cacheable(key = "#p0")
    User findByName(String name);

    /**
     * 注意@CachePut 这个注释可以确保方法被执行，同时方法的返回值也被记录到缓存中,也就是说会更新缓存。
     * @param user
     */
    @CachePut(key = "#p0.name")
    void insertOne(User user);

    List<User> findPageUsers(String name);
}
