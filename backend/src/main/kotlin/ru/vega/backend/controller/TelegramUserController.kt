package ru.vega.backend.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.vega.backend.exception.EntityNotFoundException
import ru.vega.backend.mapper.TelegramUserMapper
import ru.vega.backend.service.TelegramUserService
import ru.vega.model.dto.user.TelegramUserDto

@RestController
@RequestMapping("/telegram/user")
class TelegramUserController(
    private val telegramUserService: TelegramUserService,
    private val telegramUserMapper: TelegramUserMapper
) {

    @GetMapping("/search/{telegramId}")
    fun getByTelegramId(@PathVariable telegramId: Long): ResponseEntity<TelegramUserDto> {
        val telegramUser = telegramUserService.getTelegramUserByTelegramId(telegramId) ?:
            throw EntityNotFoundException("User with telegram id $telegramId not found!")
        val telegramUserDto = telegramUserMapper.toDto(telegramUser)
        return ResponseEntity.ok(telegramUserDto)
    }
}