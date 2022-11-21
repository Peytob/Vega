package ru.vega.backend.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.vega.backend.exception.EntityNotFoundException
import ru.vega.backend.mapper.TelegramUserMapper
import ru.vega.backend.service.TelegramUserService
import ru.vega.model.dto.user.CreateTelegramUserDto
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

    @PostMapping
    fun create(@RequestBody createTelegramUserDto: CreateTelegramUserDto): ResponseEntity<TelegramUserDto> {
        val telegramUser = telegramUserService.createTelegramUser(createTelegramUserDto)
        val telegramUsrDto = telegramUserMapper.toDto(telegramUser)
        return ResponseEntity.ok(telegramUsrDto)
    }
}