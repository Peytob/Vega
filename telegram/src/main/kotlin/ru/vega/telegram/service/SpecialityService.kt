package ru.vega.telegram.service

import ru.vega.model.dto.speciality.SpecialityDto
import java.util.UUID

interface SpecialityService {

    fun getById(id: UUID): SpecialityDto?
}
