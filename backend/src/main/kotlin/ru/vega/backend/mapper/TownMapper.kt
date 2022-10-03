package ru.vega.backend.mapper

import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper
import ru.vega.backend.entity.DistrictEntity
import ru.vega.backend.entity.TownEntity
import ru.vega.model.dto.town.DistrictDto
import ru.vega.model.dto.town.TownDto

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR)
abstract class TownMapper {

    abstract fun toDto(district: DistrictEntity): DistrictDto

    abstract fun toDto(district: TownEntity): TownDto

    fun asString(townEntity: TownEntity): String = townEntity.title!!
}
