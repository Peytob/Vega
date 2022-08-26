package ru.vega.model.dto.discipline

data class DisciplineDto(
    val externalId: String,
    val title: String,
    val description: String,
    val fipiLink: String,
    val lastMiddleScore: Int,
    val passingScore: Int,
    val questionsNumber: Int,
    val sdamGiaLink: String,
    val descriptionYear: Int
)
