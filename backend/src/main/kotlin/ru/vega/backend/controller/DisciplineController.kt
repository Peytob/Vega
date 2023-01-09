package ru.vega.backend.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.vega.backend.exception.EntityNotFoundException
import ru.vega.backend.mapper.DisciplineMapper
import ru.vega.backend.service.DisciplineCrudService
import ru.vega.model.dto.discipline.DisciplineDto
import ru.vega.model.enumeration.EducationGrade
import java.util.*

@RestController
@RequestMapping("/discipline")
class DisciplineController(
    private val disciplineCrudService: DisciplineCrudService,
    private val disciplineMapper: DisciplineMapper
) {

    @GetMapping
    fun getAll(@RequestParam(required = false) filter: EducationGrade?): ResponseEntity<Collection<DisciplineDto>> {
        val disciplines = if (filter != null) {
            disciplineCrudService.getByAllEducationGrade(filter)
        } else {
            disciplineCrudService.getAll()
        }

        return ResponseEntity.ok(disciplineMapper.toDisciplineDto(disciplines))
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: UUID): ResponseEntity<DisciplineDto> {
        val discipline = disciplineCrudService.getById(id) ?: throw EntityNotFoundException(id, "discipline")
        return ResponseEntity.ok(disciplineMapper.toDisciplineDto(discipline))
    }
}