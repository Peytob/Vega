package ru.vega.backend.service

import org.springframework.stereotype.Service
import ru.vega.backend.entity.TelegramUserEntity
import ru.vega.backend.mapper.TelegramUserMapper
import ru.vega.backend.repository.TelegramUserRepository
import ru.vega.model.dto.user.CreateTelegramUserDto

@Service
class TelegramUserServiceImpl(
    private val telegramUserRepository: TelegramUserRepository,
    private val telegramUserMapper: TelegramUserMapper
) : TelegramUserService {

    override fun getTelegramUserByTelegramId(telegramId: Long): TelegramUserEntity? =
        telegramUserRepository.findByTelegramId(telegramId)

    override fun createTelegramUser(createTelegramUserDto: CreateTelegramUserDto): TelegramUserEntity {
        val entity = telegramUserMapper.toEntity(createTelegramUserDto)
        return telegramUserRepository.save(entity)
    }
}
