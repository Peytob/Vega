package ru.vega.telegram.service

import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.LoadingCache
import dev.inmo.tgbotapi.types.UserId
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.stereotype.Service
import ru.vega.telegram.model.entity.Session
import java.util.concurrent.TimeUnit

@Service
class SessionServiceImpl : SessionService {

    companion object {
        private val logger: Logger = LogManager.getLogger()
        private const val SESSION_CACHE_TIME: Long = 10L
    }

    // Or just use Spring Starter Cache?
    private val cache: LoadingCache<UserId, Session> = Caffeine.newBuilder()
        .initialCapacity(100)
        .expireAfterAccess(SESSION_CACHE_TIME, TimeUnit.MINUTES)
        .build {
            logger.info("Creating new session for $it")
            Session(it)
        }

    override fun getOrStartSession(userId: UserId) = cache[userId]!!

    override fun isSessionActive(userId: UserId) = cache.getIfPresent(userId) != null
}