package ru.vega.backend.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.vega.backend.entity.*
import ru.vega.backend.repository.MiddleSpecialityRepository
import ru.vega.backend.repository.UniversitySpecialityRepository
import java.util.*

@Service
class UniversitySpecialityCrudServiceImpl(
    private val universitySpecialityRepository: UniversitySpecialityRepository,
    private val middleSpecialityRepository: MiddleSpecialityRepository
) : UniversitySpecialityCrudService {

    override fun getMiddleSpecialitiesPage(
        speciality: SpecialityEntity,
        pageable: Pageable
    ): Page<MiddleSpecialityEntity> =
        middleSpecialityRepository.findBySpeciality(speciality, pageable)

    override fun getMiddleById(id: UUID): MiddleSpecialityEntity? =
        middleSpecialityRepository.findByIdOrNull(id)

    override fun search(
        disciplinesSet: DisciplinesSetEntity,
        scoreFilter: Int,
        includeBudget: Boolean,
        includeContract: Boolean,
        pageable: Pageable): Page<UniversitySpecialityEntity> =
        universitySpecialityRepository.search(disciplinesSet, scoreFilter, includeBudget, includeContract, pageable)

    override fun getById(id: UUID): UniversitySpecialityEntity? =
        universitySpecialityRepository.findByIdOrNull(id)

    override fun getByUniversity(university: UniversityEntity, pageable: Pageable): Page<UniversitySpecialityEntity> =
        universitySpecialityRepository.findByUniversity(university, pageable)
}
