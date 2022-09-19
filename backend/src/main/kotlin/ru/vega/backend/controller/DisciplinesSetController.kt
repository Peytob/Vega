package ru.vega.backend.controller

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
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
import javax.validation.constraints.Min

@RestController
@RequestMapping("/disciplinesSet")
class DisciplinesSetController(
    private val disciplinesSetCrudService: DisciplinesSetCrudService,
    private val disciplineCrudService: DisciplineCrudService,
    private val disciplinesSetsMapper: DisciplinesSetsMapper,
    private val universitySpecialityCrudService: UniversitySpecialityCrudService,
    private val universitySpecialityMapper: UniversitySpecialityMapper
) {

    @GetMapping
    fun findByDisciplines(
        @RequestParam(name = "discipline") disciplinesExternalIds: Set<String>
    ): ResponseEntity<Collection<DisciplinesSetDto>> {
        val disciplinesEntities = disciplineCrudService.getAllByExternalId(disciplinesExternalIds)
        val disciplinesSets = disciplinesSetCrudService.findDisciplinesSetByDisciplines(disciplinesEntities)
        return ResponseEntity.ok(disciplinesSetsMapper.toDisciplinesSetDto(disciplinesSets))
    }

    @GetMapping("/{disciplinesSetExternalId}/specialities")
    fun findUniversitySpecialitiesByDisciplinesSet(
        @PathVariable disciplinesSetExternalId: String,
        @RequestParam(value = "page", defaultValue = "0") @Min(0) page: Int,
        @RequestParam(value = "size", defaultValue = "10") @Min(1) size: Int,
    ): ResponseEntity<Page<UniversitySpecialityDto>> {
        val disciplinesSet = disciplinesSetCrudService.getByExternalId(disciplinesSetExternalId) ?:
            throw EntityNotFoundException(disciplinesSetExternalId, "disciplineSet")
        val pageable: Pageable = PageRequest.of(page, size)
        val universitySpecialities = universitySpecialityCrudService
            .getByDisciplineSet(disciplinesSet, pageable)
            .map(universitySpecialityMapper::toDto)
        return ResponseEntity.ok(universitySpecialities)
    }
}
