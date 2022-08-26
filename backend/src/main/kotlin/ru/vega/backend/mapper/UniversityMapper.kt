package ru.vega.backend.mapper

import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import ru.vega.backend.entity.UniversityEntity
import ru.vega.model.dto.university.UniversityDto

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR)
interface UniversityMapper {

    @Mapping(target = "town", source = "town.title")
    fun toUniversityDto(university: UniversityEntity): UniversityDto

    fun toUniversityDto(universities: Collection<UniversityEntity>): Collection<UniversityDto>
}
