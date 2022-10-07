package ru.vega.backend.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.vega.backend.exception.EntityNotFoundException
import ru.vega.backend.mapper.UniversitySpecialityMapper
import ru.vega.backend.service.UniversitySpecialityCrudService
import ru.vega.model.dto.university.UniversitySpecialityDto

@RestController
@RequestMapping("/universitySpeciality")
class UniversitySpecialityController(
    private val universitySpecialityMapper: UniversitySpecialityMapper,
    private val universitySpecialityCrudService: UniversitySpecialityCrudService
) {

    @GetMapping("/{externalId}")
    fun getByExternalId(@PathVariable externalId: String): ResponseEntity<UniversitySpecialityDto> {
        val universitySpecialityEntity = universitySpecialityCrudService.getByExternalId(externalId) ?:
            throw EntityNotFoundException(externalId, "universitySpeciality")
        val universitySpecialityDto = universitySpecialityMapper.toDto(universitySpecialityEntity)
        return ResponseEntity.ok(universitySpecialityDto)
    }
}
