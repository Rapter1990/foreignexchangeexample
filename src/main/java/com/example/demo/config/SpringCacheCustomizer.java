package com.example.demo.config;

import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.example.demo.utils.Constants.EXCHANGE_CACHE_NAME;

/**
 * Customizes the configuration of the ConcurrentMapCacheManager.
 * This customizer sets the cache names and configures the cache manager to disallow null values.
 */
@Component
public class SpringCacheCustomizer implements CacheManagerCustomizer<ConcurrentMapCacheManager> {

    /**
     * Customizes the provided ConcurrentMapCacheManager by setting cache names and allowing null values.
     *
     * @param cacheManager the ConcurrentMapCacheManager to customize.
     */
    @Override
    public void customize(ConcurrentMapCacheManager cacheManager) {
        cacheManager.setCacheNames(List.of(EXCHANGE_CACHE_NAME));
        cacheManager.setAllowNullValues(false);
    }
}