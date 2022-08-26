package ru.vega.backend.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.vega.backend.exception.EntityNotFoundException
import ru.vega.backend.mapper.DisciplineMapper
import ru.vega.backend.service.DisciplineCrudService
import ru.vega.model.dto.discipline.DisciplineDto
import java.util.UUID

@RestController
@RequestMapping("/discipline")
class DisciplineController(
    private val disciplineCrudService: DisciplineCrudService,
    private val disciplineMapper: DisciplineMapper
) {

    @GetMapping
    fun getAll(): Collection<DisciplineDto> {
        val disciplines = disciplineCrudService.getAll()
        return disciplineMapper.toDisciplineDto(disciplines)
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: UUID): DisciplineDto {
        val discipline = disciplineCrudService.getById(id) ?: throw EntityNotFoundException(id, "discipline")
        return disciplineMapper.toDisciplineDto(discipline)
    }
}
