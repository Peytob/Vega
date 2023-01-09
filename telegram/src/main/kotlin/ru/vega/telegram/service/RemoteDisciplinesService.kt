package ru.vega.telegram.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import ru.vega.model.dto.discipline.DisciplineDto
import ru.vega.model.enumeration.EducationGrade
import java.util.UUID

@Service
class RemoteDisciplinesService(
    @Qualifier("backendRestTemplate")
    private val restTemplate: RestTemplate
) : DisciplinesService {

    companion object {
        private val logger = LoggerFactory.getLogger(RemoteDisciplinesService::class.java)
    }

    @Cacheable("Disciplines")
    override fun getById(id: UUID): DisciplineDto? {
        logger.info("Updating discipline with id {} from remote", id)

        return restTemplate
            .getForObject("/higher/discipline/{id}", DisciplineDto::class.java, id)
    }

    @Cacheable("MiddleDisciplines")
    override fun getMiddle(): Collection<DisciplineDto> {
        logger.info("Getting all middle disciplines from remote")
        return getDisciplines(EducationGrade.MIDDLE)
    }

    @Cacheable("HigherDisciplines")
    override fun getHigher(): Collection<DisciplineDto> {
        logger.info("Getting all higher disciplines from remote")
        return getDisciplines(EducationGrade.HIGH)
    }

    private fun getDisciplines(educationGrade: EducationGrade): Collection<DisciplineDto> =
        restTemplate
            .getForObject("/discipline?filter={educationGrade}", Array<DisciplineDto>::class.java, educationGrade)!!
            .toList()
}
