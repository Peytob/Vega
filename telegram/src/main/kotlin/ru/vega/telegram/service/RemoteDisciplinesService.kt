package ru.vega.telegram.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import ru.vega.model.dto.discipline.DisciplineDto

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
            .getForObject("/discipline", Array<DisciplineDto>::class.java)!!
            .toList()
    }

    @Cacheable("Disciplines")
    override fun getByExternalId(externalId: String): DisciplineDto? {
        logger.info("Updating discipline with external id {} from remote", externalId)

        return restTemplate
            .getForObject("/discipline/$externalId", DisciplineDto::class.java)
    }
}
