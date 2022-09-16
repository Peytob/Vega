package ru.vega.telegram.model.menu

import com.fasterxml.jackson.annotation.JsonProperty

data class SpecialitySelectMenuArgument(
    @field:JsonProperty("eid")
    val argumentExternalId: String
)
