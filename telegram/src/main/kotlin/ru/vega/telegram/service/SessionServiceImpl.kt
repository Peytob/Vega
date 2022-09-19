package ru.vega.telegram.service

import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.LoadingCache
import dev.inmo.tgbotapi.types.UserId
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Service
import ru.vega.telegram.configuration.CacheProperties
import ru.vega.telegram.model.entity.Session

@Service
@EnableConfigurationProperties(CacheProperties::class)
class SessionServiceImpl(
    cacheProperties: CacheProperties
) : SessionService {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(SessionServiceImpl::class.java)
    }

    // Or just use Spring Starter Cache?
    private val cache: LoadingCache<UserId, Session> = Caffeine.newBuilder()
        .initialCapacity(100)
        .expireAfterAccess(cacheProperties.session)
        .build {
            logger.info("Creating new session for $it")
            Session(it)
        }

    override fun getOrStartSession(userId: UserId) =
        cache[userId]!!

    override fun isSessionActive(userId: UserId) =
        cache.getIfPresent(userId) != null
}
