package ru.vega.backend.mapper

import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import ru.vega.backend.entity.StudentUser
import ru.vega.backend.entity.User
import ru.vega.model.dto.user.CreateTelegramUserDto
import ru.vega.model.dto.user.TelegramUserDto

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR)
interface UserMapper {

    fun toDto(user: User): TelegramUserDto

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", constant = "STUDENT")
    fun toEntity(createTelegramUserDto: CreateTelegramUserDto): StudentUser
}
