package ru.vega.backend.entity

import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "user_tutor_request")
open class UserTutorRequest(

    @get:ManyToOne(optional = false)
    open var user: User? = null,

    @get:ManyToOne(optional = false)
    open var tutor: TutorEntity? = null,

    @get:Column(updatable = false, nullable = false)
    open var creationDate: Instant? = null
) : AbstractEntity()
