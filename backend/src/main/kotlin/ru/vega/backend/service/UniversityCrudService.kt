package ru.vega.backend.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import ru.vega.backend.entity.UniversityEntity

interface UniversityCrudService {

    fun getPage(titleFilter: String, pageable: Pageable): Page<UniversityEntity>

    fun getByExternalId(id: String): UniversityEntity?
}