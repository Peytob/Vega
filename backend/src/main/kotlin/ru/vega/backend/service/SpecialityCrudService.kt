package ru.vega.backend.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import ru.vega.backend.entity.SpecialityEntity

interface SpecialityCrudService {

    fun getByExternalId(id: String): SpecialityEntity?

    fun getPage(titleFilter: String, pageable: Pageable): Page<SpecialityEntity>
}
