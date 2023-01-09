package ru.vega.backend.controller.middle

import org.apache.logging.log4j.util.Strings
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
import ru.vega.backend.mapper.DirectionMapper
import ru.vega.backend.mapper.SpecialityMapper
import ru.vega.backend.service.DirectionCrudService
import ru.vega.backend.service.SpecialityCrudService
import ru.vega.model.dto.direction.DirectionDto
import ru.vega.model.dto.speciality.SpecialityDto
import java.util.UUID
import javax.validation.constraints.Min

@RestController
@RequestMapping("/middle/direction")
class DirectionController(
    private val directionCrudService: DirectionCrudService,
    private val directionMapper: DirectionMapper,
    private val specialityCrudService: SpecialityCrudService,
    private val specialityMapper: SpecialityMapper
) {

    @GetMapping
    fun getDirectionsPage(@RequestParam(defaultValue = Strings.EMPTY) titleFilter: String,
            @RequestParam(value = "page", defaultValue = "0") @Min(0) page: Int,
            @RequestParam(value = "size", defaultValue = "10") @Min(1) size: Int,
            @RequestParam(value = "sortDir", defaultValue = "ASC") sortDir: Sort.Direction
    ): ResponseEntity<Page<DirectionDto>> {
        val pageable = PageRequest.of(page, size, Sort.by(sortDir, "title"))
        val directionsPage = directionCrudService.getPage(titleFilter, pageable)
        val directions = directionsPage.map(directionMapper::toDto)
        return ResponseEntity.ok(directions)
    }

    @GetMapping("/{id}/specialities")
    fun getDirectionSpecialities(@PathVariable id: UUID,
            @RequestParam(value = "page", defaultValue = "0") @Min(0) page: Int,
            @RequestParam(value = "size", defaultValue = "10") @Min(1) size: Int,
            @RequestParam(value = "sortDir", defaultValue = "ASC") sortDir: Sort.Direction
    ): ResponseEntity<Page<SpecialityDto>> {
        val pageable = PageRequest.of(page, size, Sort.by(sortDir, "title"))
        val direction = directionCrudService.getById(id) ?:
            throw EntityNotFoundException(id, "direction")
        val specialityPage = specialityCrudService.getPageByDirection(direction, pageable)
        val specialities = specialityPage.map(specialityMapper::toSpecialityDto)
        return ResponseEntity.ok(specialities)
    }
}
