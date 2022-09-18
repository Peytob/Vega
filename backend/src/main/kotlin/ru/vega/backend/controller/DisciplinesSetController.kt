package ru.vega.backend.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.vega.backend.mapper.DisciplinesSetsMapper
import ru.vega.backend.service.DisciplineCrudService
import ru.vega.backend.service.DisciplinesSetCrudService
import ru.vega.model.dto.discipline.DisciplinesSetDto

@RestController
@RequestMapping("/disciplinesSet")
class DisciplinesSetController(
    private val disciplinesSetCrudService: DisciplinesSetCrudService,
    private val disciplineCrudService: DisciplineCrudService,
    private val disciplinesSetsMapper: DisciplinesSetsMapper
) {

    @GetMapping
    fun findByDisciplines(
        @RequestParam(name = "discipline") disciplinesExternalIds: Set<String>
    ): ResponseEntity<Collection<DisciplinesSetDto>> {
        val disciplinesEntities = disciplineCrudService.getAllByExternalId(disciplinesExternalIds)
        val disciplinesSets = disciplinesSetCrudService.findDisciplinesSetByDisciplines(disciplinesEntities)
        return ResponseEntity.ok(disciplinesSetsMapper.toDisciplinesSetDto(disciplinesSets))
    }
}
