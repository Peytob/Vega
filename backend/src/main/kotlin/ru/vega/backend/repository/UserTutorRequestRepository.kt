package ru.vega.backend.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.vega.backend.entity.User
import ru.vega.backend.entity.UserTutorRequest
import ru.vega.backend.entity.TutorEntity
import java.util.UUID

interface UserTutorRequestRepository : JpaRepository<UserTutorRequest, UUID> {

    fun existsByUserAndTutor(user: User, tutor: TutorEntity): Boolean
}
