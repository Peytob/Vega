package ru.vega.model.dto.university

data class UniversityDto(
    val externalId: String,
    val title: String,
    val shortTitle: String,
    val description: String,
    val address: String,
    val site: String,
    val town: String
)
