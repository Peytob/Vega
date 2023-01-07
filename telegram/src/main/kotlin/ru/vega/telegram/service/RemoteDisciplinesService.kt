package ru.vega.telegram.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import ru.vega.model.dto.discipline.DisciplineDto
import java.util.UUID

@Service
class RemoteDisciplinesService(
    @Qualifier("backendRestTemplate")
    private val restTemplate: RestTemplate
) : DisciplinesService {

    companion object {
        private val logger = LoggerFactory.getLogger(RemoteDisciplinesService::class.java)
    }

    @Cacheable("AllDisciplines")
    override fun getAll(): Collection<DisciplineDto> {
        logger.info("Getting all disciplines from remote")

        return restTemplate
            .getForObject("/higher/discipline", Array<DisciplineDto>::class.java)!!
            .toList()
    }

    @Cacheable("Disciplines")
    override fun getById(id: UUID): DisciplineDto? {
        logger.info("Updating discipline with id {} from remote", id)

        return restTemplate
            .getForObject("/higher/discipline/{id}", DisciplineDto::class.java, id)
    }
}
