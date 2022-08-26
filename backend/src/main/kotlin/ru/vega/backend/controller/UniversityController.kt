package ru.vega.backend.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.vega.backend.exception.EntityNotFoundException
import ru.vega.backend.mapper.UniversityMapper
import ru.vega.backend.service.UniversityCrudService
import ru.vega.model.dto.university.UniversityDto

@RestController
@RequestMapping("/university")
class UniversityController(
    private val universityCrudService: UniversityCrudService,
    private val universityMapper: UniversityMapper
) {

    @GetMapping
    fun getAll(): ResponseEntity<Collection<UniversityDto>> {
        val universities = universityCrudService.getAll()
        return ResponseEntity.ok(universityMapper.toUniversityDto(universities))
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: String): ResponseEntity<UniversityDto> {
        val university = universityCrudService.getByExternalId(id) ?: throw EntityNotFoundException(id, "university")
        return ResponseEntity.ok(universityMapper.toUniversityDto(university))
    }
}