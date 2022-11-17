package ru.vega.backend.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "telegram_user")
open class TelegramUserEntity(
    @get:Column(nullable = true)
    open var firstName: String? = null,

    @get:Column(nullable = true)
    open var lastName: String? = null,

    @get:Column(updatable = false, unique = true)
    open var telegramId: Long? = null,

    @get:Column(updatable = false, unique = true)
    open var username: String? = null
) : AbstractEntity()
