package ru.vega.backend.service

import org.springframework.stereotype.Service
import ru.vega.backend.entity.TelegramUserEntity
import ru.vega.backend.entity.TelegramUserRequestEntity
import ru.vega.backend.entity.TutorEntity
import ru.vega.backend.exception.EntityAlreadyExistsException
import ru.vega.backend.repository.TelegramUserRequestRepository
import java.time.Instant

@Service
class TelegramUserServiceImpl(
    private val telegramUserRequestRepository: TelegramUserRequestRepository
) : TelegramUserService {

    override fun createTutorRequest(student: TelegramUserEntity, tutor: TutorEntity): TelegramUserRequestEntity {
        if (telegramUserRequestRepository.existsByTelegramUserAndTutor(student, tutor)) {
            throw EntityAlreadyExistsException("Request from user with id ${student.id} to tutor with id ${tutor.id} already exists!")
        }

        val request = TelegramUserRequestEntity(student, tutor, Instant.now())
        return telegramUserRequestRepository.save(request)
    }
}