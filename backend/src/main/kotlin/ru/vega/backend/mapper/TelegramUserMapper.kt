package ru.vega.backend.mapper

import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper
import ru.vega.backend.entity.TelegramUserEntity
import ru.vega.model.dto.user.TelegramUserDto

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR)
interface TelegramUserMapper {

    fun toDto(telegramUserEntity: TelegramUserEntity): TelegramUserDto
}
