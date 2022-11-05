package ru.vega.backend.mapper

import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper
import org.mapstruct.Named
import ru.vega.backend.entity.AbstractEntity
import java.util.UUID

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR)
abstract class AbstractEntityToIdMapper {

    @Named("extractId")
    fun extractId(entity: AbstractEntity): UUID = entity.id!!
}
