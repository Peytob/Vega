package ru.vega.telegram.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.cache.annotation.Cacheable
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import ru.vega.model.dto.direction.DirectionDto
import ru.vega.model.dto.speciality.SpecialityDto
import ru.vega.model.utils.Page
import ru.vega.model.utils.Pageable
import java.util.*

@Service
class RemoteSpecialityService(
    @Qualifier("backendRestTemplate")
    private val restTemplate: RestTemplate
) : SpecialityService {

    private companion object {
        private val logger = LoggerFactory.getLogger(RemoteSpecialityService::class.java)
    }

    @Cacheable("SpecialityById")
    override fun getById(id: UUID): SpecialityDto? {
        logger.info("Updating speciality with id $id")
        return restTemplate
            .getForObjectOrNull("/higher/speciality/{id}", SpecialityDto::class.java, id)
    }

    override fun getByDirection(direction: DirectionDto, pageable: Pageable): Page<SpecialityDto> {
        logger.info("Updating specialities for direction $direction for page $pageable")

        val uri = UriComponentsBuilder
            .fromUriString("/middle/direction/{directionId}/specialities")
            .queryParam("page", pageable.page)
            .queryParam("size", pageable.size)
            .buildAndExpand(direction.id)
            .toUriString()

        val typeReference = object : ParameterizedTypeReference<Page<SpecialityDto>>() {}

        return restTemplate.exchange(
            uri,
            HttpMethod.GET,
            null,
            typeReference
        ).body!!
    }
}
