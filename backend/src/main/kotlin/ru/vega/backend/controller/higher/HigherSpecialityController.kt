package ru.vega.backend.controller.higher

import org.apache.logging.log4j.util.Strings.EMPTY
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.vega.backend.exception.EntityNotFoundException
import ru.vega.backend.mapper.SpecialityMapper
import ru.vega.backend.service.SpecialityCrudService
import ru.vega.model.dto.speciality.SpecialityDto
import java.util.*
import javax.validation.constraints.Min

@RestController
@RequestMapping("/higher/speciality")
class HigherSpecialityController(
    private val specialityCrudService: SpecialityCrudService,
    private val specialityMapper: SpecialityMapper
) {

    @GetMapping
    fun getAll(@RequestParam(defaultValue = EMPTY) titleFilter: String,
               @RequestParam(value = "page", defaultValue = "0") @Min(0) page: Int,
               @RequestParam(value = "size", defaultValue = "10") @Min(1) size: Int,
               @RequestParam(value = "sortDir", defaultValue = "ASC") sortDir: Sort.Direction
    ): ResponseEntity<Page<SpecialityDto>> {
        val pageable: Pageable = PageRequest.of(page, size, Sort.by(sortDir, "title"))
        val specialitiesEntitiesPage = specialityCrudService.getPage(titleFilter, pageable)
        val specialities = specialitiesEntitiesPage.map(specialityMapper::toSpecialityDto)
        return ResponseEntity.ok(specialities)
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: UUID): ResponseEntity<SpecialityDto> {
        val speciality = specialityCrudService.getById(id)
            ?: throw EntityNotFoundException(id, "speciality")
        return ResponseEntity.ok(specialityMapper.toSpecialityDto(speciality))
    }
}