package ru.vega.backend.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import ru.vega.backend.entity.QuestionEntity
import java.util.*

interface QuestionCrudService {

    fun getPage(pageable: Pageable): Page<QuestionEntity>
    fun getById(id: UUID): QuestionEntity?
}