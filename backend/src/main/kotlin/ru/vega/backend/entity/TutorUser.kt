package ru.vega.backend.entity

import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue(value = "TUTOR")
open class TutorUser : User()
