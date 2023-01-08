package ru.vega.backend.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "direction")
open class DirectionEntity(
    @get:Column(nullable = false)
    open var title: String? = null
) : AbstractEntity()
