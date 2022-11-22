package ru.vega.model.dto.user

import java.util.UUID

data class CreateTutorRequest(
    val studentId: UUID,
    val tutorId: UUID
)
