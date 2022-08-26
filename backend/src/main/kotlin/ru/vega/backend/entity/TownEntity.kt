package ru.vega.backend.entity

import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "town")
open class TownEntity(
    open var title: String? = null
) : AbstractEntity()
