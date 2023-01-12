package ru.vega.backend.controller

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import ru.vega.backend.exception.EntityNotFoundException
import ru.vega.backend.mapper.TownMapper
import ru.vega.backend.mapper.UniversityMapper
import ru.vega.backend.service.TownCrudService
import ru.vega.backend.service.UniversityCrudService
import ru.vega.model.dto.town.DistrictDto
import ru.vega.model.dto.town.TownDto
import ru.vega.model.dto.university.UniversityDto
import ru.vega.model.enumeration.EducationGrade
import ru.vega.model.enumeration.TownType
import java.util.UUID
import javax.validation.constraints.Min

@RestController
@RequestMapping("/town")
class TownController(
    private val townCrudService: TownCrudService,
    private val universityCrudService: UniversityCrudService,
    private val townMapper: TownMapper,
    private val universityMapper: UniversityMapper
) {

    @GetMapping("/legacy/filtered")
    fun getFilteredTowns(
        @RequestParam(required = false) typeFilter: TownType?,
        @RequestParam(required = false) gradeFilter: EducationGrade?,
        @RequestParam(value = "page", defaultValue = "0") @Min(0) page: Int,
        @RequestParam(value = "size", defaultValue = "10") @Min(1) size: Int,
        @RequestParam(value = "sortDir", defaultValue = "ASC") sortDir: Sort.Direction
    ): ResponseEntity<Page<TownDto>> {

        val pageable = PageRequest.of(page, size)
        val townEntitiesPage = when {
            typeFilter == null && gradeFilter == null -> {
                townCrudService.getTownPage(pageable)
            }

            typeFilter != null && gradeFilter != null -> {
                // TODO fix it!
                townCrudService.getTownsWithUniversities(pageable, gradeFilter, typeFilter)
            }

            typeFilter != null -> {
                townCrudService.getTownsByType(pageable, typeFilter)
            }

            else -> {
                throw ResponseStatusException(HttpStatus.BAD_REQUEST, "This filters combination not allowed! Maybe, i will fix it")
            }
        }

        val towns = townEntitiesPage.map(townMapper::toDto)
        return ResponseEntity.ok(towns)
    }

    @GetMapping
    fun getTownPage(
        @RequestParam(value = "page", defaultValue = "0") @Min(0) page: Int,
        @RequestParam(value = "size", defaultValue = "10") @Min(1) size: Int,
        @RequestParam(value = "sortDir", defaultValue = "ASC") sortDir: Sort.Direction
    ): ResponseEntity<Page<TownDto>> {
        val pageable = PageRequest.of(page, size, Sort.by(sortDir, "title"))
        val townEntitiesPage = townCrudService.getTownPage(pageable)
        val towns = townEntitiesPage.map(townMapper::toDto)
        return ResponseEntity.ok(towns)
    }

    @GetMapping("/{id}/district")
    fun getDistrictsByTown(
        @PathVariable id: UUID,
        @RequestParam(value = "page", defaultValue = "0") @Min(0) page: Int,
        @RequestParam(value = "size", defaultValue = "10") @Min(1) size: Int,
        @RequestParam(value = "sortDir", defaultValue = "ASC") sortDir: Sort.Direction
    ): ResponseEntity<Page<DistrictDto>> {
        val pageable = PageRequest.of(page, size, Sort.by(sortDir, "title"))
        val town = townCrudService.getTownById(id) ?:
            throw EntityNotFoundException(id, "Town")
        val districtEntitiesPage = townCrudService.getDistrictPageByTown(town, pageable)
        val districts = districtEntitiesPage.map(townMapper::toDto)
        return ResponseEntity.ok(districts)
    }

    @GetMapping("/{id}/university")
    fun getUniversitiesByTown(
        @PathVariable id: UUID,
        @RequestParam(value = "page", defaultValue = "0") @Min(0) page: Int,
        @RequestParam(value = "size", defaultValue = "10") @Min(1) size: Int,
        @RequestParam(value = "sortDir", defaultValue = "ASC") sortDir: Sort.Direction
    ): ResponseEntity<Page<UniversityDto>> {
        val pageable = PageRequest.of(page, size, Sort.by(sortDir, "title"))
        val town = townCrudService.getTownById(id) ?:
            throw EntityNotFoundException(id, "Town")
        val universitiesEntitiesPage = universityCrudService.getPageByTown(town, pageable)
        val universities = universitiesEntitiesPage.map(universityMapper::toDto)
        return ResponseEntity.ok(universities)
    }
}