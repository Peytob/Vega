package ru.vega.telegram.service

import dev.inmo.tgbotapi.types.Identifier
import dev.inmo.tgbotapi.types.User
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import ru.vega.model.dto.user.CreateTelegramUserDto
import ru.vega.model.dto.user.TelegramUserDto

@Service
class RemoteUserService(
    @Qualifier("backendRestTemplate")
    private val restTemplate: RestTemplate
) : UserService {

    companion object {
        private val logger = LoggerFactory.getLogger(RemoteUserService::class.java)
    }

    @Cacheable("TelegramUserByTelegramId")
    override fun getUser(telegramId: Identifier): TelegramUserDto? {
        logger.info("Updating remote telegram user entity with telegram id {} from remote", telegramId)

        return restTemplate
            .getForObjectOrNull("/user/telegram/{telegramId}", TelegramUserDto::class.java, telegramId)
    }

    override fun createUser(user: User) {
        logger.info("Creating user record")

        val createDto = CreateTelegramUserDto(
            user.id.chatId,
            user.firstName,
            user.lastName,
            user.username?.username
        )

        restTemplate
            .postForObject("/user/telegram", createDto, TelegramUserDto::class.java)
    }
}
