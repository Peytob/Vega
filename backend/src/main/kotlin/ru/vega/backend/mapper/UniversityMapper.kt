package ru.vega.backend.mapper

import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper
import ru.vega.backend.entity.UniversityEntity
import ru.vega.model.dto.university.UniversityDto

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    uses = [TownMapper::class])
interface UniversityMapper {

    fun toDto(university: UniversityEntity): UniversityDto

    fun toDto(universities: Collection<UniversityEntity>): Collection<UniversityDto>
}
