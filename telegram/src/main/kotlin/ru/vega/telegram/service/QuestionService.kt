package ru.vega.telegram.service

import ru.vega.model.dto.faq.QuestionDto
import ru.vega.model.utils.Page
import ru.vega.model.utils.Pageable

interface QuestionService {

    fun get(pageable: Pageable): Page<QuestionDto>

    fun get(externalId: String): QuestionDto?
}
