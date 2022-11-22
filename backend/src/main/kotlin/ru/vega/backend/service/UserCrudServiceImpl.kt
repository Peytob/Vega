package ru.vega.backend.service

import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.vega.backend.entity.User
import ru.vega.backend.exception.EntityAlreadyExistsException
import ru.vega.backend.mapper.UserMapper
import ru.vega.backend.repository.UserRepository
import ru.vega.model.dto.user.CreateTelegramUserDto
import java.util.*

@Service
class UserCrudServiceImpl(
    private val userRepository: UserRepository,
    private val userMapper: UserMapper
) : UserCrudService {

    companion object {
        private val logger = LoggerFactory.getLogger(UserCrudServiceImpl::class.java)
    }

    override fun getTelegramUserByTelegramId(telegramId: Long): User? =
        userRepository.findByTelegramId(telegramId)

    override fun createTelegramUser(createTelegramUserDto: CreateTelegramUserDto): User {
        val telegramId = createTelegramUserDto.telegramId
        val username = createTelegramUserDto.username

        if (userRepository.existsByTelegramIdOrUsername(telegramId, username)) {
            throw EntityAlreadyExistsException("Telegram user with telegram id $telegramId or username $username already exists!")
        }

        logger.info("Creating new telegram user with telegram id {} and username {}", telegramId, username)

        val entity = userMapper.toEntity(createTelegramUserDto)
        return userRepository.save(entity)
    }

    override fun getById(id: UUID): User? =
        userRepository.findByIdOrNull(id)

    override fun getPage(usernameFilter: String, pageable: Pageable): Page<User> =
        userRepository.findAllByUsernameContainingIgnoreCase(usernameFilter, pageable)
}
