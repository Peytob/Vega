package ru.vega.model.dto.university

import java.util.*

data class UniversityDto(
    val id: UUID,
    val title: String,
    val shortTitle: String,
    val description: String,
    val address: String,
    val site: String,
    val town: String
)
