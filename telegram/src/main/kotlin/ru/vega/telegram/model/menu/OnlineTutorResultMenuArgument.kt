package ru.vega.telegram.model.menu

import com.fasterxml.jackson.annotation.JsonProperty

data class OnlineTutorResultMenuArgument(
    @JsonProperty("p")
    val page: Int,
    @JsonProperty("did")
    val disciplineExternalId: String
)
