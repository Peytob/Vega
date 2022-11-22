package ru.vega.backend.service

import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.vega.backend.entity.TelegramUserEntity
import ru.vega.backend.exception.EntityAlreadyExistsException
import ru.vega.backend.mapper.TelegramUserMapper
import ru.vega.backend.repository.TelegramUserRepository
import ru.vega.model.dto.user.CreateTelegramUserDto
import java.util.*

@Service
class TelegramUserCrudServiceImpl(
    private val telegramUserRepository: TelegramUserRepository,
    private val telegramUserMapper: TelegramUserMapper
) : TelegramUserCrudService {

    companion object {
        private val logger = LoggerFactory.getLogger(TelegramUserCrudServiceImpl::class.java)
    }

    override fun getTelegramUserByTelegramId(telegramId: Long): TelegramUserEntity? =
        telegramUserRepository.findByTelegramId(telegramId)

    override fun createTelegramUser(createTelegramUserDto: CreateTelegramUserDto): TelegramUserEntity {
        val telegramId = createTelegramUserDto.telegramId
        val username = createTelegramUserDto.username

        if (telegramUserRepository.existsByTelegramIdOrUsername(telegramId, username)) {
            throw EntityAlreadyExistsException("Telegram user with telegram id $telegramId or username $username already exists!")
        }

        logger.info("Creating new telegram user with telegram id {} and username {}", telegramId, username)

        val entity = telegramUserMapper.toEntity(createTelegramUserDto)
        return telegramUserRepository.save(entity)
    }

    override fun getById(id: UUID): TelegramUserEntity? =
        telegramUserRepository.findByIdOrNull(id)

    override fun getPage(usernameFilter: String, pageable: Pageable): Page<TelegramUserEntity> =
        telegramUserRepository.findAllByUsernameContainingIgnoreCase(usernameFilter, pageable)
}
