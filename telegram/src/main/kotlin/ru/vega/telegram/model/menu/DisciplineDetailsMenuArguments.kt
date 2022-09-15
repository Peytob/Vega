package ru.vega.telegram.model.menu

import com.fasterxml.jackson.annotation.JsonProperty

data class DisciplineDetailsMenuArguments(
    @field:JsonProperty("did")
    val disciplineExternalId: String
)
