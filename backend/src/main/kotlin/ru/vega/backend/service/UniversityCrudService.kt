package ru.vega.backend.service

import ru.vega.backend.entity.UniversityEntity

interface UniversityCrudService {

    fun getAll(): Collection<UniversityEntity>

    fun getByExternalId(id: String): UniversityEntity?
}