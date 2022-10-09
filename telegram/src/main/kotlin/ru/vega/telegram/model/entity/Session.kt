package ru.vega.telegram.model.entity

import dev.inmo.tgbotapi.types.UserId

data class Session(
    val telegram_user: UserId,
    val selectedDisciplinesIds: MutableSet<String> = mutableSetOf(),
    val selectedTutors: MutableSet<String> = mutableSetOf(),
    val tutorsToSendMessage: MutableSet<String> = mutableSetOf(),
    var totalScore: Int? = null,

    var tutorDiscipline: String? = null,
    var tutorTown: String? = null,
    var tutorDistrict: String? = null
)
