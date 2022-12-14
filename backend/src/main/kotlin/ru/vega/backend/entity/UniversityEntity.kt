package ru.vega.backend.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "university")
open class UniversityEntity(

    @get:Column(nullable = false)
    open var title: String? = null,

    @get:Column(nullable = false)
    open var shortTitle: String? = null,

    @get:Column(nullable = false, columnDefinition = "TEXT")
    open var description: String? = null,

    @get:Column(nullable = false)
    open var address: String? = null,

    @get:Column(nullable = false)
    open var site: String? = null,

    @get:ManyToOne(optional = false)
    open var town: TownEntity? = null
) : AbstractEntity()
