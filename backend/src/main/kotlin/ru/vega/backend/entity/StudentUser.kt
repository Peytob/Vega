package ru.vega.backend.entity

import javax.persistence.*

@Entity
@DiscriminatorValue(value = "STUDENT")
open class StudentUser(
    @get:ManyToMany
    @get:JoinTable(
        name = "user_bookmarks",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "university_speciality_id")])
    open var bookmarks: MutableCollection<UniversitySpecialityEntity>? = mutableSetOf()
) : User()
