package ru.vega.backend.mapper

import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper
import ru.vega.backend.entity.QuestionEntity
import ru.vega.model.dto.faq.QuestionDto

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR)
interface QuestionMapper {

    fun toDto(questionEntity: QuestionEntity): QuestionDto
}
