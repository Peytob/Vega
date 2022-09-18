package ru.vega.backend.mapper

import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import ru.vega.backend.entity.DisciplinesSetEntity
import ru.vega.model.dto.discipline.DisciplinesSetDto

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    uses = [AbstractEntityToIdMapper::class])
interface DisciplinesSetsMapper {

    @Mapping(source = "disciplines", target = "disciplines", qualifiedByName = ["extractId"])
    fun toDisciplinesSetDto(disciplinesSets: DisciplinesSetEntity): DisciplinesSetDto

    fun toDisciplinesSetDto(disciplinesSets: Collection<DisciplinesSetEntity>): Collection<DisciplinesSetDto>
}
