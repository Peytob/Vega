package ru.vega.telegram.model.menu

import com.fasterxml.jackson.annotation.JsonProperty

data class OnlineTutorDetailsMenuArgument(
    @JsonProperty("tid")
    val tutorExternalId: String,

    @JsonProperty("p")
    val page: Int,

    @JsonProperty("did")
    val disciplineId: String
)
