package ru.vega.telegram.model.menu

import com.fasterxml.jackson.annotation.JsonProperty

data class TutorTownSelectArgument(
    val page: Int,
    @field:JsonProperty("deid")
    val disciplineExternalId: String
)
