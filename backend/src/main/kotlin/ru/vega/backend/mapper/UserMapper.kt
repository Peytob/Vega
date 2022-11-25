package ru.vega.backend.mapper

import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import ru.vega.backend.entity.StudentUser
import ru.vega.backend.entity.TutorUser
import ru.vega.backend.entity.User
import ru.vega.model.dto.user.CreateTelegramUserDto
import ru.vega.model.dto.user.TelegramUserDto

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    uses = [AbstractEntityToIdMapper::class])
abstract class UserMapper {

    @Mapping(target = "bookmarksSpecialities", source = "bookmarks", qualifiedByName = ["extractId"])
    abstract fun toDto(user: StudentUser): TelegramUserDto

    @Mapping(target = "bookmarksSpecialities", ignore = true)
    abstract fun toDto(user: TutorUser): TelegramUserDto

    fun toDto(user: User): TelegramUserDto = when(user) {
        is StudentUser -> toDto(user)
        is TutorUser -> toDto(user)
        else -> throw ClassCastException()
    }

    @Mappings(
        Mapping(target = "id", ignore = true),
        Mapping(target = "patronymic", ignore = true)
    )
    abstract fun toEntity(createTelegramUserDto: CreateTelegramUserDto): StudentUser
}
