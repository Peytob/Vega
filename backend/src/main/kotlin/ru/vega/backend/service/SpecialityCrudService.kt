package ru.vega.backend.service

import ru.vega.backend.entity.SpecialityEntity
import java.util.*

interface SpecialityCrudService {

    fun getAll(): Collection<SpecialityEntity>

    fun getById(id: UUID): SpecialityEntity?
}
