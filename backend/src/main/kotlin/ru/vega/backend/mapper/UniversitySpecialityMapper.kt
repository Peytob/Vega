package ru.vega.backend.mapper

import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import ru.vega.backend.entity.UniversitySpecialityEntity
import ru.vega.model.dto.university.UniversitySpecialityDto

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    uses = [AbstractEntityToIdMapper::class])
interface UniversitySpecialityMapper {

    @Mapping(target = "disciplinesSets", qualifiedByName = ["extractId"])
    @Mapping(target = "speciality", qualifiedByName = ["extractId"])
    @Mapping(target = "university", qualifiedByName = ["extractId"])
    fun toDto(universitySpecialityEntity: UniversitySpecialityEntity): UniversitySpecialityDto
}
