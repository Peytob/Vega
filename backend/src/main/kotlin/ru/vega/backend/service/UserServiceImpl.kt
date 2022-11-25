package ru.vega.backend.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import ru.vega.backend.entity.*
import ru.vega.backend.exception.BadEntityTypeException
import ru.vega.backend.exception.EntityAlreadyExistsException
import ru.vega.backend.exception.EntityNotFoundException
import ru.vega.backend.mapper.UserMapperImpl
import ru.vega.backend.repository.UserRepository
import ru.vega.backend.repository.UserTutorRequestRepository
import java.time.Instant

@Service
class UserServiceImpl(
    private val userTutorRequestRepository: UserTutorRequestRepository,
    private val userRepository: UserRepository
) : UserService {

    companion object {
        private val logger = LoggerFactory.getLogger(UserMapperImpl::class.java)
    }

    @Transactional
    override fun createTutorRequest(student: User, tutor: TutorEntity): UserTutorRequest {
        if (userTutorRequestRepository.existsByUserAndTutor(student, tutor)) {
            throw EntityAlreadyExistsException(
                "Request from user with id ${student.id} to tutor with id ${tutor.id} already exists!")
        }

        val request = UserTutorRequest(student, tutor, Instant.now())
        return userTutorRequestRepository.save(request)
    }

    @Transactional
    override fun createBookmark(telegramUser: User, universitySpeciality: UniversitySpecialityEntity) {
        if (telegramUser !is StudentUser) {
            throw BadEntityTypeException("Telegram user should be student!")
        }

        if (telegramUser.bookmarks!!.any { it.id == universitySpeciality.id }) {
            throw EntityAlreadyExistsException("Bookmark already exists!")
        }

        telegramUser.bookmarks!!.add(universitySpeciality)

        userRepository.save(telegramUser)
        logger.info("Bookmark {} for user {} created", universitySpeciality.id, telegramUser.id)
    }

    @Transactional
    override fun deleteBookmark(telegramUser: User, universitySpeciality: UniversitySpecialityEntity) {
        if (telegramUser !is StudentUser) {
            throw BadEntityTypeException("Telegram user should be student!")
        }

        if (telegramUser.bookmarks!!.none { it.id == universitySpeciality.id }) {
            throw EntityNotFoundException("Bookmark not found")
        }

        telegramUser.bookmarks!!.removeIf { it.id == universitySpeciality.id }

        userRepository.save(telegramUser)
        logger.info("Bookmark {} for user {} removed", universitySpeciality.id, telegramUser.id)
    }
}