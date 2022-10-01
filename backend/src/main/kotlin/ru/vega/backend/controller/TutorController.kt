package ru.vega.backend.controller

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.vega.backend.exception.EntityNotFoundException
import ru.vega.backend.mapper.TutorMapper
import ru.vega.backend.service.DisciplineCrudService
import ru.vega.backend.service.TownCrudService
import ru.vega.backend.service.TutorCrudService
import ru.vega.model.dto.tutor.TutorDto
import javax.validation.constraints.Min

@RestController
@RequestMapping("/tutor")
class TutorController(
    private val disciplineCrudService: DisciplineCrudService,
    private val townCrudService: TownCrudService,
    private val tutorCrudService: TutorCrudService,
    private val tutorMapper: TutorMapper
) {

    @GetMapping("/search")
    fun getByDistrict(@RequestParam(required = true) districtId: String,
                      @RequestParam(required = true) disciplineId: String,
                      @RequestParam(value = "page", defaultValue = "0") @Min(0) page: Int,
                      @RequestParam(value = "size", defaultValue = "10") @Min(1) size: Int,
                      @RequestParam(value = "sortDir", defaultValue = "ASC") sortDir: Sort.Direction):
            ResponseEntity<Page<TutorDto>> {
        val pageable = PageRequest.of(page, size)

        val discipline = disciplineCrudService.getByExternalId(disciplineId) ?:
            throw EntityNotFoundException(disciplineId, "Discipline")

        val district = townCrudService.getDistrictByExternalId(districtId) ?:
            throw EntityNotFoundException(districtId, "District")

        val tutors = tutorCrudService
            .getByDistrictAndDiscipline(district, discipline, pageable)
            .map(tutorMapper::toDto)

        return ResponseEntity.ok(tutors)
    }

    @GetMapping("/{externalId}")
    fun get(@PathVariable externalId: String): ResponseEntity<TutorDto> {
        val tutor = tutorCrudService.getByExternalId(externalId) ?:
            throw EntityNotFoundException(externalId, "Tutor")

        return ResponseEntity.ok(tutorMapper.toDto(tutor))
    }
}
