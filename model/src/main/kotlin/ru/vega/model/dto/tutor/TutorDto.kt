package ru.vega.model.dto.tutor

import ru.vega.model.dto.discipline.DisciplineDto
import ru.vega.model.dto.town.DistrictDto
import java.util.UUID

data class TutorDto(
    val id: UUID,

    val district: DistrictDto,

    val notificationChatId: Long,

    val address: String,

    val name: String,

    val email: String,

    val phone: String,

    val offline: Boolean,

    val online: Boolean,

    val telegram: String?,

    val whatsApp: String?,

    val disciplines: Collection<DisciplineDto>,

    val photoUrl: String,

    val description: String,

    val price: Int
)
