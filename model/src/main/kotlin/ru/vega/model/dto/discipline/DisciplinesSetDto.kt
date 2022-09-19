package ru.vega.model.dto.discipline

data class DisciplinesSetDto(
    val externalId: String,
    val title: String,
    val disciplines: Collection<String>
)
