package ru.vega.backend.entity

import javax.persistence.*

@Entity
@Table(name = "disciplines_set")
open class DisciplinesSetEntity(

    @get:Column(unique = true, nullable = false)
    open var title: String? = null,

    @get:ManyToMany
    @get:JoinTable(
        name = "disciplines_set_discipline_link",
        joinColumns = [JoinColumn(name = "set_id")],
        inverseJoinColumns = [JoinColumn(name = "discipline_id")])
    open var disciplines: Set<DisciplineEntity>? = mutableSetOf()
) : AbstractEntity()
