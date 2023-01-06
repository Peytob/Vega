package ru.vega.backend.controller.higher

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.vega.backend.exception.EntityNotFoundException
import ru.vega.backend.mapper.DisciplinesSetsMapper
import ru.vega.backend.mapper.UniversitySpecialityMapper
import ru.vega.backend.service.DisciplineCrudService
import ru.vega.backend.service.DisciplinesSetCrudService
import ru.vega.backend.service.UniversitySpecialityCrudService
import ru.vega.model.dto.discipline.DisciplinesSetDto
import ru.vega.model.dto.university.UniversitySpecialityDto
import java.util.UUID
import javax.validation.constraints.Min
import javax.validation.constraints.NotEmpty

@RestController
@RequestMapping("/higher/disciplinesSet")
class HigherDisciplinesSetController(
    private val disciplinesSetCrudService: DisciplinesSetCrudService,
    private val disciplineCrudService: DisciplineCrudService,
    private val disciplinesSetsMapper: DisciplinesSetsMapper,
    private val universitySpecialityCrudService: UniversitySpecialityCrudService,
    private val universitySpecialityMapper: UniversitySpecialityMapper
) {

    @GetMapping
    fun findByDisciplines(
        @RequestParam(name = "discipline") @NotEmpty disciplinesIds: Set<UUID>
    ): ResponseEntity<Collection<DisciplinesSetDto>> {
        val disciplinesEntities = disciplineCrudService.getById(disciplinesIds)
        val disciplinesSets = disciplinesSetCrudService.findDisciplinesSetByDisciplines(disciplinesEntities)
        return ResponseEntity.ok(disciplinesSetsMapper.toDisciplinesSetDto(disciplinesSets))
    }

    @GetMapping("/{disciplinesSetId}/specialities")
    fun findUniversitySpecialitiesByDisciplinesSet(
        @PathVariable disciplinesSetId: UUID,
        @RequestParam(value = "scoreFilter", defaultValue = Int.MAX_VALUE.toString()) @Min(0) scoreFilter: Int,
        @RequestParam(value = "page", defaultValue = "0") @Min(0) page: Int,
        @RequestParam(value = "size", defaultValue = "10") @Min(1) size: Int,
        @RequestParam(value = "includeBudget", defaultValue = "true") includeBudget: Boolean,
        @RequestParam(value = "includeContract", defaultValue = "true") includeContract: Boolean
    ): ResponseEntity<Page<UniversitySpecialityDto>> {
        val disciplinesSet = disciplinesSetCrudService.getById(disciplinesSetId) ?:
            throw EntityNotFoundException(disciplinesSetId, "disciplineSet")
        val pageable: Pageable = PageRequest.of(page, size)
        val universitySpecialities = universitySpecialityCrudService
            .search(disciplinesSet, scoreFilter, includeBudget, includeContract, pageable)
            .map(universitySpecialityMapper::toDto)
        return ResponseEntity.ok(universitySpecialities)
    }
}
