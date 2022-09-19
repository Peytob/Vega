package ru.vega.backend.mapper

import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper
import ru.vega.backend.entity.UniversitySpecialityEntity
import ru.vega.model.dto.university.UniversitySpecialityDto

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    uses = [DisciplinesSetsMapper::class, UniversityMapper::class])
interface UniversitySpecialityMapper {

    fun toDto(universitySpecialityEntity: UniversitySpecialityEntity): UniversitySpecialityDto
}
