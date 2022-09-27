package ru.vega.backend.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "district")
open class DistrictEntity(

    @get:ManyToOne(optional = false)
    open var town: TownEntity? = null,

    @get:Column(nullable = false)
    open var title: String? = null
) : AbstractEntity()
