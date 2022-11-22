package ru.vega.backend.entity

import ru.vega.model.enumeration.UserRole
import javax.persistence.*

@Entity
@Table(name = "\"user\"")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING)
abstract class User(
    @get:Column(nullable = true)
    open var firstName: String? = null,

    @get:Column(nullable = true)
    open var lastName: String? = null,

    @get:Column(nullable = false, updatable = false, unique = true)
    open var telegramId: Long? = null,

    @get:Column(nullable = true, updatable = false)
    open var username: String? = null,

    @get:Column(nullable = false)
    open var role: UserRole? = null
) : AbstractEntity()
