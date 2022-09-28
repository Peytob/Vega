package ru.vega.backend.mapper

import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper
import ru.vega.backend.entity.TutorEntity
import ru.vega.model.dto.tutor.TutorDto

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    uses = [TownMapper::class, DisciplineMapper::class])
interface TutorMapper {

    fun toDto(tutorEntity: TutorEntity): TutorDto
}
