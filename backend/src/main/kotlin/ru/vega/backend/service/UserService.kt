package ru.vega.backend.service

import ru.vega.backend.entity.User
import ru.vega.backend.entity.UserTutorRequest
import ru.vega.backend.entity.TutorEntity

interface UserService {
    fun createTutorRequest(student: User, tutor: TutorEntity): UserTutorRequest

}
