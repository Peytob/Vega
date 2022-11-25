package ru.vega.model.dto.tutor

import ru.vega.model.dto.discipline.DisciplineDto
import java.util.*

data class TutorDto(
    val id: UUID,
    val notificationChatId: Long,
    val patronymic: String,
    val forename: String,
    val surname: String,
    val telegram: String?,
    val disciplines: Collection<DisciplineDto>,
    val photoUrl: String,
    val description: String,
)
