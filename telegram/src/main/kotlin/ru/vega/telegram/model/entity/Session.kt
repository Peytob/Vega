package ru.vega.telegram.model.entity

import dev.inmo.tgbotapi.types.MessageIdentifier
import dev.inmo.tgbotapi.types.User
import ru.vega.telegram.model.enums.EducationForm
import ru.vega.telegram.model.enums.TutorMeetingForm
import java.util.*

data class Session(
    val messageIdentifier: MessageIdentifier,
    val user: User,
    val menuHistory: MenuHistory,
    val speciality: SessionSpeciality = SessionSpeciality(),
    val tutor: SessionTutor = SessionTutor()
)

data class SessionSpeciality(
    val selectedDisciplines: MutableSet<UUID> = mutableSetOf(),
    var educationForm: Set<EducationForm> = emptySet(),
    var score: Int? = null
)

data class SessionTutor(
    var meetingForms: Set<TutorMeetingForm> = setOf()
)
