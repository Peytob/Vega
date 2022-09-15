package ru.vega.telegram.model.menu

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.JsonNode

// Json names is short, because telegram has limits in 64 bytes per callback
data class MenuCallbackCommand(
    @field:JsonProperty("nmi")
    val nextMenuId: String? = null,
    @field:JsonProperty("md")
    val menuData: JsonNode? = null
)
