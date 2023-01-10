package ru.vega.backend.entity

import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "middle_speciality")
open class MiddleSpecialityEntity(

    @get:ManyToOne(optional = false)
    open var speciality: SpecialityEntity,

    @get:ManyToOne(optional = false)
    open var university: UniversityEntity,

    open var grade9acceptance: Boolean,

    open var grade11acceptance: Boolean,

    open var middleScore: Float? = null,

    open var budgetPlaces: Int? = null,

    open var contractPlaces: Int? = null,

    open var absentiaPrice: Int? = null,

    open var intramuralPrice: Int? = null,

    open var partTimePrice: Int? = null
) : AbstractEntity()
