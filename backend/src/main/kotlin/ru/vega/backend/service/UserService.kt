package ru.vega.backend.service

import ru.vega.backend.entity.User
import ru.vega.backend.entity.UserTutorRequest
import ru.vega.backend.entity.TutorEntity
import ru.vega.backend.entity.UniversitySpecialityEntity

interface UserService {
    fun createTutorRequest(student: User, tutor: TutorEntity): UserTutorRequest
    fun createBookmark(telegramUser: User, universitySpeciality: UniversitySpecialityEntity)
    fun deleteBookmark(telegramUser: User, universitySpeciality: UniversitySpecialityEntity)
}
