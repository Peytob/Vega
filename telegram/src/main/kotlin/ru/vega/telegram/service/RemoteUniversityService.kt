package ru.vega.telegram.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.cache.annotation.Cacheable
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import ru.vega.model.dto.university.UniversityDto
import ru.vega.model.utils.Page
import ru.vega.model.utils.Pageable
import java.util.*

@Service
class RemoteUniversityService(
    @Qualifier("backendRestTemplate")
    private val restTemplate: RestTemplate
): UniversityService {

    companion object {
        private val logger = LoggerFactory.getLogger(RemoteUniversityService::class.java)
    }

    @Cacheable("UniversitiesByTownId")
    override fun getByTown(townId: UUID, pageable: Pageable): Page<UniversityDto> {
        logger.info("Updating available universities for page $pageable in town with id $townId")

        val uri = UriComponentsBuilder
            .fromUriString("/town/{townId}/university")
            .queryParam("page", pageable.page)
            .queryParam("size", pageable.size)
            .buildAndExpand(townId)
            .toUriString()

        val typeReference = object : ParameterizedTypeReference<Page<UniversityDto>>() {}

        return restTemplate.exchange(
            uri,
            HttpMethod.GET,
            null,
            typeReference
        ).body!!
    }

    @Cacheable("Universities")
    override fun getById(universityId: UUID): UniversityDto? {
        logger.info("Updating university with id {} from remote", universityId)

        return restTemplate
            .getForObject("/higher/university/{id}", UniversityDto::class.java, universityId)
    }
}