package ru.vega.telegram.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import ru.vega.model.dto.discipline.DisciplinesSetDto
import java.util.UUID

@Service
class RemoteDisciplinesSetService(
    @Qualifier("backendRestTemplate")
    private val restTemplate: RestTemplate
) : DisciplinesSetService {

    companion object {
        private val logger = LoggerFactory.getLogger(RemoteDisciplinesService::class.java)
    }

    @Cacheable("DisciplinesSets")
    override fun getDisciplinesSet(disciplinesIds: Set<UUID>): DisciplinesSetDto? {
        logger.info("Updating available disciplinesSet for set $disciplinesIds from remote")

        val uri = UriComponentsBuilder.fromUriString("/higher/disciplinesSet")
            .queryParam("discipline", disciplinesIds)
            .build()
            .toUriString()

        val disciplinesSets = restTemplate
            .getForObject(uri, Array<DisciplinesSetDto>::class.java)!!

        if (disciplinesSets.size != 1) {
            logger.warn("For disciplines set $disciplinesIds found more, than one disciplines set.")
        }

        return disciplinesSets.firstOrNull()
    }
}
