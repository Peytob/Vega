package ru.vega.model.dto.town

import ru.vega.model.enumeration.TownType
import java.util.UUID

data class TownDto(
    val id: UUID,
    val title: String,
    val type: TownType
)
