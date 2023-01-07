package ru.vega.backend.controller.higher

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.vega.backend.exception.EntityNotFoundException
import ru.vega.backend.mapper.UniversitySpecialityMapper
import ru.vega.backend.service.UniversitySpecialityCrudService
import ru.vega.model.dto.university.UniversitySpecialityDto
import java.util.UUID

@RestController
@RequestMapping("/higher/universitySpeciality")
class HigherUniversitySpecialityController(
    private val universitySpecialityMapper: UniversitySpecialityMapper,
    private val universitySpecialityCrudService: UniversitySpecialityCrudService
) {

    @GetMapping("/{id}")
    fun getById(@PathVariable id: UUID): ResponseEntity<UniversitySpecialityDto> {
        val universitySpecialityEntity = universitySpecialityCrudService.getById(id) ?:
            throw EntityNotFoundException(id, "universitySpeciality")
        val universitySpecialityDto = universitySpecialityMapper.toDto(universitySpecialityEntity)
        return ResponseEntity.ok(universitySpecialityDto)
    }
}
