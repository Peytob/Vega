package ru.vega.backend.entity

import ru.vega.model.enumeration.EducationGrade
import javax.persistence.*

@Entity
@Table(name = "discipline")
open class DisciplineEntity(

    @get:Column(unique = true, nullable = false)
    open var title: String? = null,

    @get:Column(nullable = false, columnDefinition = "TEXT")
    open var description: String? = null,

    @get:Column(nullable = false)
    open var fipiLink: String? = null,

    @get:Column(nullable = false)
    open var lastMiddleScore: Int? = null,

    @get:Column(nullable = false)
    open var passingScore: Int? = null,

    @get:Column(nullable = false)
    open var sdamGiaLink: String? = null,

    @get:Column(nullable = false)
    open var descriptionYear: Int? = null,

    @get:Column(nullable = false)
    @get:Enumerated(EnumType.STRING)
    open var grade: EducationGrade? = null
) : AbstractEntity()
