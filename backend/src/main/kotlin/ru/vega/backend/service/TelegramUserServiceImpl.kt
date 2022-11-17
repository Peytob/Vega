package ru.vega.backend.service

import org.springframework.stereotype.Service
import ru.vega.backend.entity.TelegramUserEntity
import ru.vega.backend.repository.TelegramUserRepository

@Service
class TelegramUserServiceImpl(
    private val telegramUserRepository: TelegramUserRepository
) : TelegramUserService {

    override fun getTelegramUserByTelegramId(telegramId: Long): TelegramUserEntity? =
        telegramUserRepository.findByTelegramId(telegramId)
}
