package ru.vega.telegram.model.menu

import com.fasterxml.jackson.annotation.JsonProperty

data class UniversityDetailsArgument(
    @field:JsonProperty("eid")
    val externalId: String,
    @field:JsonProperty("fpn")
    val fromPageNumber: Int
)
