package ru.vega.backend.entity

import javax.persistence.*

@Entity
@Table(name = "university_speciality")
open class UniversitySpecialityEntity(

    @get:ManyToOne(optional = false)
    @get:JoinColumn(name = "speciality_id")
    open var speciality: SpecialityEntity? = null,

    @get:ManyToMany
    @get:JoinTable(
        name = "disciplines_set_university_speciality_link",
        joinColumns = [JoinColumn(name = "university_speciality_id")],
        inverseJoinColumns = [JoinColumn(name = "set_id")])
    open var disciplinesSets: Collection<DisciplinesSetEntity>? = mutableSetOf(),

    @get:ManyToOne(optional = false)
    @get:JoinColumn(name = "university_id")
    open var university: UniversityEntity? = null,

    open var budgetPlaces: Int? = null,

    open var contractPlaces: Int? = null,

    open var budgetPassingScore: Int? = null,

    open var contractPassingScore: Int? = null,

    open var absentiaPrice: Int? = null,

    open var intramuralPrice: Int? = null,

    open var partTimePrice: Int? = null,

) : AbstractEntity()
