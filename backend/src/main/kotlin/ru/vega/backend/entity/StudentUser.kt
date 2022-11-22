package ru.vega.backend.entity

import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue(value = "STUDENT")
open class StudentUser : User()
