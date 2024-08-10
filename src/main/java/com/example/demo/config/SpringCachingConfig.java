package com.example.demo.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.example.demo.utils.Constants.EXCHANGE_CACHE_NAME;

/**
 * Configuration class for setting up caching in the Spring application.
 * This configuration enables caching and defines a CacheManager bean.
 */
@Configuration
@EnableCaching
public class SpringCachingConfig {

    /**
     * Creates and configures a CacheManager bean.
     * The CacheManager will manage caches with the specified cache name.
     *
     * @return a configured instance of ConcurrentMapCacheManager.
     */
    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(EXCHANGE_CACHE_NAME);
    }

}
