package ru.vega.telegram.model.entity

import dev.inmo.tgbotapi.types.MessageIdentifier
import dev.inmo.tgbotapi.types.User
import java.util.*

data class Session(
    val messageIdentifier: MessageIdentifier,
    val user: User,
    val menuHistory: MenuHistory,
    val speciality: SessionSpeciality = SessionSpeciality()
)

data class SessionSpeciality(
    val selectedDisciplines: MutableList<UUID> = mutableListOf()
)
