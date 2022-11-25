package ru.vega.backend.mapper

import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import ru.vega.backend.entity.TutorEntity
import ru.vega.model.dto.tutor.TutorDto

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    uses = [DisciplineMapper::class])
interface TutorMapper {

    @Mappings(
        Mapping(target = "patronymic", source = "user.patronymic"),
        Mapping(target = "forename", source = "user.forename"),
        Mapping(target = "surname", source = "user.surname"),
        Mapping(target = "telegram", source = "user.telegramId")
    )
    fun toDto(tutorEntity: TutorEntity): TutorDto
}
