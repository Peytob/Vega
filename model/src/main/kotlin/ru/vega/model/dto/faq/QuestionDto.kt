package ru.vega.model.dto.faq

import java.util.UUID

data class QuestionDto(
    val id: UUID,
    val quest: String,
    val answer: String
)
