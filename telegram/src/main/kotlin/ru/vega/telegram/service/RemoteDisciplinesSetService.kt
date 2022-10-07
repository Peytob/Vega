package ru.vega.telegram.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import ru.vega.model.dto.discipline.DisciplinesSetDto

@Service
class RemoteDisciplinesSetService(
    @Qualifier("backendRestTemplate")
    private val restTemplate: RestTemplate
) : DisciplinesSetService {

    companion object {
        private val logger = LoggerFactory.getLogger(RemoteDisciplinesService::class.java)
    }

    @Cacheable("DisciplinesSets")
    override fun getDisciplinesSet(disciplinesExternalIds: Set<String>): DisciplinesSetDto? {
        logger.info("Updating available disciplinesSet for set $disciplinesExternalIds from remote")

        val uri = UriComponentsBuilder.fromUriString("/disciplinesSet")
            .queryParam("discipline", disciplinesExternalIds)
            .build()
            .toUriString()

        val disciplinesSets = restTemplate
            .getForObject(uri, Array<DisciplinesSetDto>::class.java)!!

        if (disciplinesSets.size != 1) {
            logger.warn("For disciplines set $disciplinesExternalIds found more, than one disciplines set.")
        }

        return disciplinesSets.firstOrNull()
    }
}
