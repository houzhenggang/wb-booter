package com.yaoa.boot.app.config;

import org.redisson.api.RedissonClient;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.redisson.spring.session.config.EnableRedissonHttpSession;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cjh on 2017/3/12.
 */
@Configurable
@EnableRedissonHttpSession
public class SessionConfigure {

    @Bean
    public CacheManager cacheManager(RedissonClient redissonClient) {
        Map<String, CacheConfig> config = new HashMap<String, CacheConfig>();
        // 创建一个名称为"RedissonCache"的缓存，过期时间ttl为24秒钟，同时最长空闲时maxIdleTime为12秒钟。
        config.put("RedissonCache", new CacheConfig(24*60*1000, 12*60*1000));
        return new RedissonSpringCacheManager(redissonClient, config);
    }

}
