package ru.vega.backend.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.vega.backend.entity.QuestionEntity
import java.util.*

interface QuestionRepository : JpaRepository<QuestionEntity, UUID> {
    fun findByExternalId(externalId: String): QuestionEntity?
}
