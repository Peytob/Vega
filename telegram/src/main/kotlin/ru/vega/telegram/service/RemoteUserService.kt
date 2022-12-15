package ru.vega.telegram.service

import dev.inmo.tgbotapi.types.Identifier
import dev.inmo.tgbotapi.types.User
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.cache.annotation.Cacheable
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.postForEntity
import org.springframework.web.client.postForObject
import org.springframework.web.util.UriComponentsBuilder
import ru.vega.model.dto.university.UniversitySpecialityDto
import ru.vega.model.dto.user.CreateTelegramUserDto
import ru.vega.model.dto.user.TelegramUserDto
import ru.vega.model.utils.Page
import ru.vega.telegram.exception.EntityNotFound
import ru.vega.telegram.model.enums.EducationForm
import java.util.*

@Service
class RemoteUserService(
    @Qualifier("backendRestTemplate")
    private val restTemplate: RestTemplate
) : UserService {

    companion object {
        private val logger = LoggerFactory.getLogger(RemoteUserService::class.java)
    }

    // TODO Не кешируется, так как после создания пользователя его пересоздает из-за null в кеше.
    //  Добавить инвалидацию записи в кеше при создании юзера
    override fun getUser(telegramId: Identifier): TelegramUserDto? {
        logger.info("Updating remote telegram user entity with telegram id {} from remote", telegramId)

        return restTemplate
            .getForObjectOrNull("/user/telegram/{telegramId}", TelegramUserDto::class.java, telegramId)
    }

    override fun createUserRequest(userId: Identifier, tutorId: UUID): Boolean {
        return try {
            restTemplate.postForObject<CreateTelegramUserDto>(
                "/user/telegram/{userId}/request/{tutorId}", "null", userId, tutorId)
            true
        } catch (badRequest: HttpClientErrorException.BadRequest) {
            logger.warn("User {} already sent request for tutor {}", userId, tutorId)
            false
        }
    }

    override fun containsBookmark(userId: Identifier, universitySpecialityId: UUID): Boolean {
        val user = getUser(userId) ?:
            throw EntityNotFound("User with id $userId not found")

        return user.bookmarksSpecialities.contains(universitySpecialityId)
    }

    override fun deleteBookmark(userId: Identifier, universitySpecialityId: UUID) {
        try {
            restTemplate.delete(
                "/user/telegram/{userId}/bookmark/{universitySpecialityId}", userId, universitySpecialityId)
        } catch (badRequest: HttpClientErrorException.BadRequest) {
            logger.warn("Bad request while deleting bookmark")
        }
    }

    override fun createBookmark(userId: Identifier, universitySpecialityId: UUID) {
        try {
            restTemplate.postForEntity<String>(
                "/user/telegram/{userId}/bookmark/{universitySpecialityId}", "null", userId, universitySpecialityId)
        } catch (badRequest: HttpClientErrorException.BadRequest) {
            logger.warn("Bad request while deleting bookmark")
        }
    }

    @Cacheable("AllUsers")
    override fun getAllUsers(): Page<TelegramUserDto> {
        logger.info("Updating all users cache")

        val uri = UriComponentsBuilder
            .fromUriString("/user")
            .queryParam("page", 0)
            .queryParam("size", Int.MAX_VALUE)
            .toUriString()

        val typeReference = object : ParameterizedTypeReference<Page<TelegramUserDto>>() {}

        return restTemplate.exchange(
            uri,
            HttpMethod.GET,
            null,
            typeReference
        ).body!!
    }

    override fun getBookmarks(userId: Identifier): Collection<UUID>? =
        getUser(userId)?.bookmarksSpecialities

    override fun createUser(user: User) {
        logger.info("Creating user record for user {}", user)

        val createDto = CreateTelegramUserDto(
            user.id.chatId,
            user.lastName,
            user.firstName,
            user.username?.username
        )

        restTemplate
            .postForObject<TelegramUserDto>("/user/telegram", createDto)
    }
}
