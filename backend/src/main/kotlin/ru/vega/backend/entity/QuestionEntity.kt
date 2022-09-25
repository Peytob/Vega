package ru.vega.backend.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "faq")
open class QuestionEntity(
    @get:Column(nullable = false, columnDefinition = "TEXT")
    open var quest: String? = null,

    @get:Column(nullable = false, columnDefinition = "TEXT")
    open var answer: String? = null
): AbstractEntity()
