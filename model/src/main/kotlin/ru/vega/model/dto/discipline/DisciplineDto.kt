package ru.vega.model.dto.discipline

import java.util.UUID

data class DisciplineDto(
    val id: UUID,
    val title: String,
    val description: String,
    val fipiLink: String,
    val lastMiddleScore: Int,
    val passingScore: Int,
    val questionsNumber: Int,
    val sdamGiaLink: String,
    val descriptionYear: Int
)
