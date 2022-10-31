package ru.vega.model.dto.discipline

import java.util.UUID

data class DisciplinesSetDto(
    val id: UUID,
    val title: String,
    val disciplines: Collection<UUID>
)
