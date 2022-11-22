package ru.vega.backend.service

import org.springframework.stereotype.Service
import ru.vega.backend.entity.User
import ru.vega.backend.entity.UserTutorRequest
import ru.vega.backend.entity.TutorEntity
import ru.vega.backend.exception.EntityAlreadyExistsException
import ru.vega.backend.repository.UserTutorRequestRepository
import java.time.Instant

@Service
class UserServiceImpl(
    private val userTutorRequestRepository: UserTutorRequestRepository
) : UserService {

    override fun createTutorRequest(student: User, tutor: TutorEntity): UserTutorRequest {
        if (userTutorRequestRepository.existsByUserAndTutor(student, tutor)) {
            throw EntityAlreadyExistsException("Request from user with id ${student.id} to tutor with id ${tutor.id} already exists!")
        }

        val request = UserTutorRequest(student, tutor, Instant.now())
        return userTutorRequestRepository.save(request)
    }
}