package ru.vega.backend.entity

import ru.vega.model.enumeration.UserRole
import javax.persistence.*

@Entity
@Table(name = "\"USER\"")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, name = "user_role")
abstract class User(
    @get:Column(nullable = true)
    open var forename: String? = null,

    @get:Column(nullable = true)
    open var surname: String? = null,

    @get:Column(nullable = true)
    open var patronymic: String? = null,

    @get:Column(nullable = false, updatable = false, unique = true)
    open var telegramId: Long? = null,

    @get:Column(nullable = true, updatable = false)
    open var username: String? = null,

    @get:Column(name = "user_role", insertable = false, updatable = false)
    @get:Enumerated(EnumType.STRING)
    open var userRole: UserRole? = null
) : AbstractEntity()
