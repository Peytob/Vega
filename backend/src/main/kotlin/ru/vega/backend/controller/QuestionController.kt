package ru.vega.backend.controller

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.vega.backend.exception.EntityNotFoundException
import ru.vega.backend.mapper.QuestionMapper
import ru.vega.backend.service.QuestionCrudService
import ru.vega.model.dto.faq.QuestionDto
import javax.validation.constraints.Min

@RestController
@RequestMapping("/faq")
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

    @GetMapping("/{externalId}")
    fun get(externalId: String): ResponseEntity<QuestionDto> {
        val questionEntity = questionCrudService.getByExternalId(externalId) ?:
            throw EntityNotFoundException(externalId, "question")
        val question = questionMapper.toDto(questionEntity)
        return ResponseEntity.ok(question)
    }
}
