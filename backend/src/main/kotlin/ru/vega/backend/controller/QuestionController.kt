package ru.vega.backend.controller

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.vega.backend.exception.EntityNotFoundException
import ru.vega.backend.mapper.QuestionMapper
import ru.vega.backend.service.QuestionCrudService
import ru.vega.model.dto.faq.QuestionDto
import java.util.*
import javax.validation.constraints.Min

@RestController
@RequestMapping("/question")
class QuestionController(
    private val questionCrudService: QuestionCrudService,
    private val questionMapper: QuestionMapper
) {

    @GetMapping
    fun get(@RequestParam(value = "page", defaultValue = "0") @Min(0) page: Int,
            @RequestParam(value = "size", defaultValue = "10") @Min(1) size: Int,
            @RequestParam(value = "sortDir", defaultValue = "ASC") sortDir: Sort.Direction
    ): ResponseEntity<Page<QuestionDto>> {
        val pageable: Pageable = PageRequest.of(page, size, Sort.by(sortDir, "quest"))
        val questionPage = questionCrudService.getPage(pageable)
        val questions = questionPage.map(questionMapper::toDto)
        return ResponseEntity.ok(questions)
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: UUID): ResponseEntity<QuestionDto> {
        val questionEntity = questionCrudService.getById(id) ?:
            throw EntityNotFoundException(id, "question")
        val question = questionMapper.toDto(questionEntity)
        return ResponseEntity.ok(question)
    }
}
