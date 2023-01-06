package ru.vega.backend.controller.higher

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.vega.backend.exception.EntityNotFoundException
import ru.vega.backend.mapper.DisciplineMapper
import ru.vega.backend.service.DisciplineCrudService
import ru.vega.model.dto.discipline.DisciplineDto
import java.util.*

@RestController
@RequestMapping("/higher/discipline")
class HigherDisciplineController(
    private val disciplineCrudService: DisciplineCrudService,
    private val disciplineMapper: DisciplineMapper
) {

    @GetMapping
    fun getAll(): ResponseEntity<Collection<DisciplineDto>> {
        val disciplines = disciplineCrudService.getAll()
        return ResponseEntity.ok(disciplineMapper.toDisciplineDto(disciplines))
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: UUID): ResponseEntity<DisciplineDto> {
        val discipline = disciplineCrudService.getById(id) ?: throw EntityNotFoundException(id, "discipline")
        return ResponseEntity.ok(disciplineMapper.toDisciplineDto(discipline))
    }
}