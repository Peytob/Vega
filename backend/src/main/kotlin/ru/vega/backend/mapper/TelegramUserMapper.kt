package ru.vega.backend.mapper

import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import ru.vega.backend.entity.TelegramUserEntity
import ru.vega.model.dto.user.CreateTelegramUserDto
import ru.vega.model.dto.user.TelegramUserDto

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR)
interface TelegramUserMapper {

    fun toDto(telegramUserEntity: TelegramUserEntity): TelegramUserDto

    @Mapping(target = "id", ignore = true)
    fun toEntity(createTelegramUserDto: CreateTelegramUserDto): TelegramUserEntity
}
