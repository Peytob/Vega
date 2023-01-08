package ru.vega.backend.entity

import ru.vega.model.enumeration.EducationLevel
import javax.persistence.*

@Entity
@Table(name = "speciality")
open class SpecialityEntity(

    @get:Column(nullable = false)
    open var title: String? = null,

    @get:Column(nullable = false)
    open var code: String? = null,

    @get:Column(columnDefinition = "TEXT")
    open var description: String? = null,

    @get:ManyToOne
    open var direction: DirectionEntity? = null,

    // Фактически, код специальности уже содержит информацию о том, что по уровню образования
    @get:Enumerated(EnumType.STRING)
    @get:Column(nullable = false)
    open var educationLevel: EducationLevel? = null,
) : AbstractEntity()
