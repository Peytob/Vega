package ru.vega.backend.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.vega.backend.entity.TownEntity
import ru.vega.backend.entity.UniversityEntity
import ru.vega.backend.repository.UniversityRepository
import ru.vega.model.enumeration.EducationGrade
import java.util.*

@Service
class UniversityCrudServiceImpl(
    private val universityRepository: UniversityRepository
) : UniversityCrudService {

    override fun getPage(titleFilter: String, pageable: Pageable, grade: EducationGrade) =
        universityRepository.findAllByTitleContainingIgnoreCaseAndGrade(titleFilter, pageable, grade)

    override fun getPage(titleFilter: String, pageable: Pageable) =
        universityRepository.findAllByTitleContainingIgnoreCase(titleFilter, pageable)

    override fun getById(id: UUID): UniversityEntity? =
        universityRepository.findByIdOrNull(id)

    override fun getPageByTown(town: TownEntity, pageable: Pageable): Page<UniversityEntity> =
        universityRepository.findByTown(town, pageable)
}