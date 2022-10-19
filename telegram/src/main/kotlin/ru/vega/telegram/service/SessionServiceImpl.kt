package ru.vega.telegram.service

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import dev.inmo.tgbotapi.extensions.utils.extensions.raw.from
import dev.inmo.tgbotapi.types.MessageIdentifier
import dev.inmo.tgbotapi.types.message.abstracts.Message
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Service
import ru.vega.telegram.configuration.CacheProperties
import ru.vega.telegram.model.entity.MenuHistory
import ru.vega.telegram.model.entity.Session

@Service
@EnableConfigurationProperties(CacheProperties::class)
class SessionServiceImpl(
    cacheProperties: CacheProperties
) : SessionService {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(SessionServiceImpl::class.java)
    }

    private val cache: Cache<MessageIdentifier, Session> = Caffeine.newBuilder()
        .initialCapacity(100)
        .expireAfterAccess(cacheProperties.session)
        .build()

    override fun getSession(message: Message): Session? =
        cache.getIfPresent(message.messageId)

    override fun startSession(message: Message): Session {
        logger.info("Starting session for message $message")

        val session = Session(
            message.messageId,
            message.from!!,
            MenuHistory()
        )

        cache.put(message.messageId, session)

        return session
    }

    override fun isSessionActive(message: Message) =
        cache.getIfPresent(message.messageId) != null
}
