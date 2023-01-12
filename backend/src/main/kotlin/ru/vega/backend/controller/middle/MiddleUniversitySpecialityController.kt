package ru.vega.backend.controller.middle

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.vega.backend.exception.EntityNotFoundException
import ru.vega.backend.mapper.UniversitySpecialityMapper
import ru.vega.backend.service.SpecialityCrudService
import ru.vega.backend.service.UniversitySpecialityCrudService
import ru.vega.model.dto.university.MiddleSpecialityDto
import java.util.*
import javax.validation.constraints.Min

@RestController
@RequestMapping("/middle/universitySpeciality")
class MiddleUniversitySpecialityController(
    private val specialityCrudService: SpecialityCrudService,
    private val universitySpecialityMapper: UniversitySpecialityMapper,
    private val universitySpecialityCrudService: UniversitySpecialityCrudService
) {

    @GetMapping("/search")
    fun getPageBySpeciality(@RequestParam(name = "speciality") specialityId: UUID,
                            @RequestParam(value = "page", defaultValue = "0") @Min(0) page: Int,
                            @RequestParam(value = "size", defaultValue = "10") @Min(1) size: Int,
                            @RequestParam(value = "sortDir", defaultValue = "ASC") sortDir: Sort.Direction
    ) : ResponseEntity<Page<MiddleSpecialityDto>> {

        val speciality = specialityCrudService.getById(specialityId) ?:
            throw EntityNotFoundException(specialityId, "speciality")

        val pageable = PageRequest.of(page, size, Sort.by(sortDir, "university_title"))
        val middleSpecialitiesPage = universitySpecialityCrudService.getMiddleSpecialitiesPage(speciality, pageable)
        val middleSpecialities = middleSpecialitiesPage.map(universitySpecialityMapper::toDto)
        return ResponseEntity.ok(middleSpecialities)
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: UUID) : ResponseEntity<MiddleSpecialityDto> {

        val speciality = universitySpecialityCrudService.getMiddleById(id) ?:
            throw EntityNotFoundException(id, "middleSpeciality")

        return ResponseEntity.ok(universitySpecialityMapper.toDto(speciality))
    }
}
