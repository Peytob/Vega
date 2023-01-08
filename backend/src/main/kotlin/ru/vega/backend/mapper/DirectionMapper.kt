package ru.vega.backend.mapper

import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper
import ru.vega.backend.entity.DirectionEntity
import ru.vega.model.dto.direction.DirectionDto

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR)
interface DirectionMapper {

    fun toDto(directionEntity: DirectionEntity): DirectionDto
}
