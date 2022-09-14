package ru.vega.telegram.model.menu

import com.fasterxml.jackson.databind.JsonNode

data class NextMenuData(
    val nextMenuId: String,
    val data: JsonNode
)
