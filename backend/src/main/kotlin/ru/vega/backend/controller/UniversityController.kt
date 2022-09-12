package ru.vega.backend.controller

import org.apache.logging.log4j.util.Strings
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.vega.backend.exception.EntityNotFoundException
import ru.vega.backend.mapper.UniversityMapper
import ru.vega.backend.service.UniversityCrudService
import ru.vega.model.dto.university.UniversityDto
import javax.validation.constraints.Min

@RestController
@RequestMapping("/university")
class UniversityController(
    private val universityCrudService: UniversityCrudService,
    private val universityMapper: UniversityMapper
) {

    @GetMapping
    fun get(@RequestParam(defaultValue = Strings.EMPTY) filter: String,
            @RequestParam(value = "page", defaultValue = "0") @Min(0) page: Int,
            @RequestParam(value = "size", defaultValue = "10") @Min(1) size: Int,
            @RequestParam(value = "sortDir", defaultValue = "ASC") sortDir: Sort.Direction
    ): ResponseEntity<Page<UniversityDto>> {
        val pageable = PageRequest.of(page, size, Sort.by(sortDir, "title"))
        val universitiesEntitiesPage = universityCrudService.getPage(filter, pageable)
        val universities = universitiesEntitiesPage.map(universityMapper::toUniversityDto)
        return ResponseEntity.ok(universities)
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: String): ResponseEntity<UniversityDto> {
        val university = universityCrudService.getByExternalId(id) ?: throw EntityNotFoundException(id, "university")
        return ResponseEntity.ok(universityMapper.toUniversityDto(university))
    }
}