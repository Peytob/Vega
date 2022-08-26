package ru.vega.backend.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import ru.vega.backend.exception.EntityNotFoundException
import ru.vega.backend.mapper.SpecialityMapper
import ru.vega.backend.service.SpecialityCrudService
import ru.vega.model.dto.speciality.SpecialityDto

@RestController
class SpecialityController(
    private val specialityCrudService: SpecialityCrudService,
    private val specialityMapper: SpecialityMapper
) {

    @GetMapping
    fun getAll(): ResponseEntity<Collection<SpecialityDto>> {
        val specialities = specialityCrudService.getAll()
        return ResponseEntity.ok(specialityMapper.toSpecialityDto(specialities))
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: String): ResponseEntity<SpecialityDto> {
        val speciality = specialityCrudService.getByExternalId(id) ?: throw EntityNotFoundException(id, "speciality")
        return ResponseEntity.ok(specialityMapper.toSpecialityDto(speciality))
    }
}