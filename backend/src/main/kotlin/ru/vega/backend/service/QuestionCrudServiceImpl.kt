package ru.vega.backend.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.vega.backend.entity.QuestionEntity
import ru.vega.backend.repository.QuestionRepository
import java.util.*

@Service
class QuestionCrudServiceImpl(
    private val questionRepository: QuestionRepository
) : QuestionCrudService {

    override fun getPage(pageable: Pageable): Page<QuestionEntity> =
        questionRepository.findAll(pageable)

    override fun getById(id: UUID): QuestionEntity? =
        questionRepository.findByIdOrNull(id)
}