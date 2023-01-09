package ru.vega.telegram.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import ru.vega.model.dto.direction.DirectionDto
import ru.vega.model.utils.Page
import ru.vega.model.utils.Pageable

@Service
class RemoteDirectionService(
    @Qualifier("backendRestTemplate")
    private val restTemplate: RestTemplate
) : DirectionService {

    private companion object {
        private val logger = LoggerFactory.getLogger(RemoteDirectionService::class.java)
    }

    override fun getDirectionsPage(pageable: Pageable): Page<DirectionDto> {
        logger.info("Updating directions page $pageable")

        val uri = UriComponentsBuilder
            .fromUriString("/middle/direction")
            .queryParam("page", pageable.page)
            .queryParam("size", pageable.size)
            .toUriString()

        val typeReference = object : ParameterizedTypeReference<Page<DirectionDto>>() {}

        return restTemplate.exchange(
            uri,
            HttpMethod.GET,
            null,
            typeReference
        ).body!!
    }
}