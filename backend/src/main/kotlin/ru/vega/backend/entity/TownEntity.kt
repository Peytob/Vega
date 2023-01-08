package ru.vega.backend.entity

import ru.vega.model.enumeration.TownType
import javax.persistence.*

@Entity
@Table(name = "town")
open class TownEntity(
    @get:Column(nullable = false)
    open var title: String? = null,

    @get:Column(nullable = false)
    @get:Enumerated(EnumType.STRING)
    open var type: TownType? = null
) : AbstractEntity()
