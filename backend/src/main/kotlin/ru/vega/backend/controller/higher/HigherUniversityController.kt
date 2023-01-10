package ru.vega.backend.controller.higher

import org.apache.logging.log4j.util.Strings
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import ru.vega.backend.exception.EntityNotFoundException
import ru.vega.backend.mapper.UniversityMapper
import ru.vega.backend.mapper.UniversitySpecialityMapper
import ru.vega.backend.service.UniversityCrudService
import ru.vega.backend.service.UniversitySpecialityCrudService
import ru.vega.model.dto.university.UniversityDto
import ru.vega.model.dto.university.UniversitySpecialityDto
import ru.vega.model.enumeration.EducationGrade
import java.util.*
import javax.validation.constraints.Min

@RestController
@RequestMapping("/higher/university")
class HigherUniversityController(
    private val universityCrudService: UniversityCrudService,
    private val universityMapper: UniversityMapper,
    private val universitySpecialityCrudService: UniversitySpecialityCrudService,
    private val universitySpecialityMapper: UniversitySpecialityMapper
) {

    @GetMapping
    fun get(@RequestParam(defaultValue = Strings.EMPTY) titleFilter: String,
            @RequestParam(value = "page", defaultValue = "0") @Min(0) page: Int,
            @RequestParam(value = "size", defaultValue = "10") @Min(1) size: Int,
            @RequestParam(value = "sortDir", defaultValue = "ASC") sortDir: Sort.Direction
    ): ResponseEntity<Page<UniversityDto>> {
        val pageable = PageRequest.of(page, size, Sort.by(sortDir, "title"))
        val universitiesEntitiesPage = universityCrudService.getPage(titleFilter, pageable, EducationGrade.HIGH)
        val universities = universitiesEntitiesPage.map(universityMapper::toDto)
        return ResponseEntity.ok(universities)
    }

    @GetMapping("/{id}")
    fun getUniversity(@PathVariable id: UUID): ResponseEntity<UniversityDto> {
        val university = universityCrudService.getById(id) ?:
            throw EntityNotFoundException(id, "university")

        if (university.grade != EducationGrade.HIGH) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "This is not high grade university")
        }

        return ResponseEntity.ok(universityMapper.toDto(university))
    }

    @GetMapping("/{id}/specialities")
    fun getSpecialities(@PathVariable id: UUID,
                        @RequestParam(value = "page", defaultValue = "0") @Min(0) page: Int,
                        @RequestParam(value = "size", defaultValue = "10") @Min(1) size: Int,
                        @RequestParam(value = "sortDir", defaultValue = "ASC") sortDir: Sort.Direction
    ): ResponseEntity<Page<UniversitySpecialityDto>> {
        val pageable = PageRequest.of(page, size, Sort.by(sortDir, "speciality_title"))
        val university = universityCrudService.getById(id) ?:
            throw EntityNotFoundException(id, "university")
        val specialitiesEntities = universitySpecialityCrudService.getByUniversity(university, pageable)
        val specialities = specialitiesEntities.map(universitySpecialityMapper::toDto)
        return ResponseEntity.ok(specialities)
    }
}