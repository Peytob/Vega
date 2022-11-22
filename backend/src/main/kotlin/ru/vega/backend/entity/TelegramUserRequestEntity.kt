package ru.vega.backend.entity

import java.time.Instant
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "telegram_user_request")
open class TelegramUserRequestEntity(

    @get:OneToOne(optional = false)
    open var telegramUser: TelegramUserEntity? = null,

    @get:OneToOne(optional = false)
    open var tutor: TutorEntity? = null,

    @get:Column(updatable = false, nullable = false)
    open var creationDate: Instant? = null
) : AbstractEntity()
