package ru.vega.telegram.configuration

import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
@EnableCaching
class CacheConfiguration(
    private val cacheProperties: CacheProperties
) {

    @Bean
    fun caffeine(): Caffeine<Any, Any> =
        Caffeine.newBuilder()
            .expireAfterWrite(cacheProperties.backendData)

    @Bean
    fun cacheManager(): CacheManager {
        val caffeineCacheManager = CaffeineCacheManager()
        caffeineCacheManager.setCaffeine(caffeine())
        return caffeineCacheManager
    }
}