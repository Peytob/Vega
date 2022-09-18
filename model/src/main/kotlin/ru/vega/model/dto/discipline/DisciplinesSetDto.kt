package ru.vega.model.dto.discipline

data class DisciplinesSetDto(
    val title: String,
    val disciplines: Collection<String>
)
