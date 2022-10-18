package ru.vega.telegram.service

import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.LoadingCache
import dev.inmo.tgbotapi.extensions.utils.extensions.raw.from
import dev.inmo.tgbotapi.types.message.abstracts.Message
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

    private val cache: LoadingCache<Message, Session> = Caffeine.newBuilder()
        .initialCapacity(100)
        .expireAfterAccess(cacheProperties.session)
        .build {
            logger.info("Creating new session for ${it.from}")
            Session(it)
        }

    override fun getSession(message: Message): Session? =
        cache.getIfPresent(message)

    override fun startSession(message: Message): Session =
        cache[message]!!

    override fun isSessionActive(message: Message) =
        cache.getIfPresent(message) != null

    override fun refreshSession(message: Message) {
        cache.refresh(message)
    }
}
