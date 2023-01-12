package ru.vega.telegram.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.cache.annotation.Cacheable
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import ru.vega.model.dto.town.DistrictDto
import ru.vega.model.dto.town.TownDto
import ru.vega.model.enumeration.TownType
import ru.vega.model.utils.Page
import ru.vega.model.utils.Pageable
import java.util.*

@Service
class RemoteTownService(
    @Qualifier("backendRestTemplate")
    private val restTemplate: RestTemplate
) : TownService {

    companion object {
        private val logger = LoggerFactory.getLogger(RemoteTownService::class.java)
    }

    @Cacheable("TownPages")
    override fun getTownPage(pageable: Pageable): Page<TownDto> {
        logger.info("Updating available towns for page $pageable")

        val uri = UriComponentsBuilder
            .fromUriString("/town")
            .queryParam("page", pageable.page)
            .queryParam("size", pageable.size)
            .toUriString()

        return restTemplate.exchange(
            uri,
            HttpMethod.GET,
            null,
            object : ParameterizedTypeReference<Page<TownDto>>() {}
        ).body!!
    }

    @Cacheable("DistrictPages")
    override fun getDistrictPage(townId: UUID, pageable: Pageable): Page<DistrictDto> {
        logger.info("Updating districts of town with external id $townId for page $pageable")

        val uri = UriComponentsBuilder
            .fromUriString("/town/${townId}/district")
            .queryParam("page", pageable.page)
            .queryParam("size", pageable.size)
            .toUriString()

        return restTemplate.exchange(
            uri,
            HttpMethod.GET,
            null,
            object : ParameterizedTypeReference<Page<DistrictDto>>() {}
        ).body!!
    }

    @Cacheable("MiddleTownsPage")
    override fun getMiddleTownsPage(pageable: Pageable, townType: TownType): Page<TownDto> {
        logger.info("Updating available towns with middle grade universities for page $pageable")

        val uri = UriComponentsBuilder
            .fromUriString("/town/legacy/filtered")
            .queryParam("page", pageable.page)
            .queryParam("size", pageable.size)
            .queryParam("typeFilter", townType)
            .toUriString()

        return restTemplate.exchange(
            uri,
            HttpMethod.GET,
            null,
            object : ParameterizedTypeReference<Page<TownDto>>() {}
        ).body!!
    }
}
