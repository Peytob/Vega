package ru.vega.backend.mapper

import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper
import ru.vega.backend.entity.SpecialityEntity
import ru.vega.model.dto.speciality.SpecialityDto

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR)
interface SpecialityMapper {

    fun toSpecialityDto(speciality: SpecialityEntity): SpecialityDto

    fun toSpecialityDto(speciality: Collection<SpecialityEntity>): Collection<SpecialityDto>
}
