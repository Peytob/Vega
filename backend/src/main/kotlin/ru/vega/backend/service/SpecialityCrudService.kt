package ru.vega.backend.service

import ru.vega.backend.entity.SpecialityEntity

interface SpecialityCrudService {

    fun getAll(): Collection<SpecialityEntity>

    fun getByExternalId(id: String): SpecialityEntity?
}
