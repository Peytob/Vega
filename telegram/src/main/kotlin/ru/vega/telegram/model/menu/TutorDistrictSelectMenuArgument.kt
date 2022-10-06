package ru.vega.telegram.model.menu

import com.fasterxml.jackson.annotation.JsonProperty

data class TutorDistrictSelectMenuArgument(
    @field:JsonProperty("p")
    val page: Int,
    @field:JsonProperty("deid")
    val districtExternalId: String
)
