package ru.vega.telegram.service

import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.LoadingCache
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import ru.vega.model.dto.discipline.DisciplinesSetDto
import ru.vega.telegram.configuration.CacheProperties

@Service
class RemoteDisciplinesSetService(
    @Qualifier("backendRestTemplate")
    private val restTemplate: RestTemplate,
    cacheProperties: CacheProperties,
) : DisciplinesSetService {

    companion object {
        private val logger = LoggerFactory.getLogger(RemoteDisciplinesService::class.java)
    }

    private val cache: LoadingCache<Set<String>, DisciplinesSetDto> = Caffeine.newBuilder()
        .initialCapacity(100)
        .expireAfterAccess(cacheProperties.session)
        .build {
            logger.info("Updating available disciplinesSet for set $it")

            val uri = UriComponentsBuilder.fromUriString("/disciplinesSet")
                .queryParam("discipline", it)
                .build()
                .toUriString()

            val disciplinesSets = restTemplate
                .getForObject(uri, Array<DisciplinesSetDto>::class.java)!!

            if (disciplinesSets.size != 1) {
                logger.warn("For disciplines set $it found more, than one disciplines set.")
            }

            disciplinesSets.firstOrNull()
        }

    override fun getDisciplinesSet(disciplinesExternalIds: Set<String>): DisciplinesSetDto? =
        cache[disciplinesExternalIds]
}