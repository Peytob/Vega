package ru.vega.telegram.service

import dev.inmo.tgbotapi.types.User
import ru.vega.model.dto.tutor.TutorDto

interface NotifyService {

    suspend fun newStudentNotify(tutor: TutorDto, student: User)
}
