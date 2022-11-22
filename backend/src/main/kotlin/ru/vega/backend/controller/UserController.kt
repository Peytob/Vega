package ru.vega.backend.controller

import org.apache.logging.log4j.util.Strings
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.vega.backend.exception.EntityNotFoundException
import ru.vega.backend.mapper.UserMapper
import ru.vega.backend.service.UserCrudService
import ru.vega.backend.service.UserService
import ru.vega.backend.service.TutorCrudService
import ru.vega.model.dto.user.CreateTelegramUserDto
import ru.vega.model.dto.user.CreateTutorRequest
import ru.vega.model.dto.user.TelegramUserDto
import javax.validation.constraints.Min

@RestController
@RequestMapping("/user")
class UserController(
    private val userCrudService: UserCrudService,
    private val tutorCrudService: TutorCrudService,
    private val userService: UserService,
    private val userMapper: UserMapper
) {

    @GetMapping("/telegram/{telegramId}")
    fun getByTelegramId(@PathVariable telegramId: Long): ResponseEntity<TelegramUserDto> {
        val telegramUser = userCrudService.getTelegramUserByTelegramId(telegramId) ?:
            throw EntityNotFoundException("User with telegram id $telegramId not found!")
        val telegramUserDto = userMapper.toDto(telegramUser)
        return ResponseEntity.ok(telegramUserDto)
    }

    @GetMapping
    fun getAll(@RequestParam(defaultValue = Strings.EMPTY) usernameFilter: String,
               @RequestParam(value = "page", defaultValue = "0") @Min(0) page: Int,
               @RequestParam(value = "size", defaultValue = "10") @Min(1) size: Int,
               @RequestParam(value = "sortDir", defaultValue = "ASC") sortDir: Sort.Direction
    ): ResponseEntity<Page<TelegramUserDto>> {
        val pageable: Pageable = PageRequest.of(page, size, Sort.by(sortDir, "username"))
        val telegramUsersEntitiesPage = userCrudService.getPage(usernameFilter, pageable)
        val telegramUsers = telegramUsersEntitiesPage.map(userMapper::toDto)
        return ResponseEntity.ok(telegramUsers)
    }

    @PostMapping("/request")
    fun createTutorRequest(@RequestBody tutorRequest: CreateTutorRequest): ResponseEntity<*> {
        val tutor = tutorCrudService.getById(tutorRequest.tutorId) ?:
            throw EntityNotFoundException(tutorRequest.tutorId, "Tutor")
        val student = userCrudService.getById(tutorRequest.studentId) ?:
            throw EntityNotFoundException(tutorRequest.studentId, "TelegramUser")
        val request = userService.createTutorRequest(student, tutor)
        return ResponseEntity.ok(request)
    }

    @PostMapping("/telegram")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody createTelegramUserDto: CreateTelegramUserDto): ResponseEntity<TelegramUserDto> {
        val telegramUser = userCrudService.createTelegramUser(createTelegramUserDto)
        val telegramUsrDto = userMapper.toDto(telegramUser)
        return ResponseEntity.ok(telegramUsrDto)
    }
}