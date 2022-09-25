package ru.vega.telegram.menu

import com.fasterxml.jackson.annotation.JsonProperty

data class FaqAnswerArgument(
    @field:JsonProperty("qei")
    val questionExternalId: String,
    @field:JsonProperty("fpn")
    val fromPage: Int
)
