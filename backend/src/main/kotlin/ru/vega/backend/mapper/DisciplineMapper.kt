package ru.vega.backend.mapper

import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper
import ru.vega.backend.entity.DisciplineEntity
import ru.vega.model.dto.discipline.DisciplineDto

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR)
interface DisciplineMapper {

    fun toDisciplineDto(discipline: DisciplineEntity): DisciplineDto
    fun toDisciplineDto(disciplines: Collection<DisciplineEntity>): Collection<DisciplineDto>
}
