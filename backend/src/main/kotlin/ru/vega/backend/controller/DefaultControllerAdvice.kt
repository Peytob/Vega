package ru.vega.backend.controller

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import ru.vega.model.dto.common.ErrorDto

@RestControllerAdvice
class DefaultControllerAdvice {

    companion object {
        private val logger = LoggerFactory.getLogger(DefaultControllerAdvice::class.java)
    }

    // TODO Доработать, чтобы не отправляло конкретные типы Java / Kotlin

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun badRequestException(exception: Exception): ResponseEntity<ErrorDto> =
        makeDefaultException(exception.message, HttpStatus.BAD_REQUEST)

    @ExceptionHandler(Exception::class)
    fun anyException(exception: Exception): ResponseEntity<ErrorDto> =
        makeInternalException(exception)

    private fun makeInternalException(exception: Exception): ResponseEntity<ErrorDto> {
        logger.info("Internal exception", exception)
        return makeDefaultException("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR)
    }

    private fun makeDefaultException(message: String?, status: HttpStatus): ResponseEntity<ErrorDto> {
        logger.debug(message)

        val error = ErrorDto(
            message = message ?: "",
            type = status.name
        )

        return ResponseEntity(error, status)
    }
}
