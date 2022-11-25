package ru.vega.backend.mapper

import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import ru.vega.backend.entity.StudentUser
import ru.vega.backend.entity.User
import ru.vega.model.dto.user.CreateTelegramUserDto
import ru.vega.model.dto.user.TelegramUserDto

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR)
interface UserMapper {

    fun toDto(user: User): TelegramUserDto

    @Mappings(
        Mapping(target = "id", ignore = true),
        Mapping(target = "patronymic", ignore = true)
    )
    fun toEntity(createTelegramUserDto: CreateTelegramUserDto): StudentUser
}
