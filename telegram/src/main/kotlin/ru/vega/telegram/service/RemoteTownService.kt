package ru.vega.telegram.service

import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.LoadingCache
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import ru.vega.model.dto.town.DistrictDto
import ru.vega.model.dto.town.TownDto
import ru.vega.model.utils.Page
import ru.vega.model.utils.Pageable
import ru.vega.telegram.configuration.CacheProperties

@Service
class RemoteTownService(
    @Qualifier("backendRestTemplate")
    private val restTemplate: RestTemplate,

    cacheProperties: CacheProperties
) : TownService {

    private val townCache: LoadingCache<Pageable, Page<TownDto>> = Caffeine.newBuilder()
        .initialCapacity(100)
        .expireAfterAccess(cacheProperties.session)
        .build {
            logger.info("Updating town cache for page $it")

            val uri = UriComponentsBuilder
                .fromUriString("/town")
                .queryParam("page", it.page)
                .queryParam("size", it.size)
                .toUriString()

            val typeReference = object : ParameterizedTypeReference<Page<TownDto>>() {}

            restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                typeReference
            ).body!!
        }

    private val districtCache: LoadingCache<CacheKey, Page<DistrictDto>> = Caffeine.newBuilder()
        .initialCapacity(100)
        .expireAfterAccess(cacheProperties.session)
        .build {
            logger.info("Updating town cache for page $it")

            val uri = UriComponentsBuilder
                .fromUriString("/town/${it.townExternalId}/district")
                .queryParam("page", it.pageable.page)
                .queryParam("size", it.pageable.size)
                .toUriString()

            val typeReference = object : ParameterizedTypeReference<Page<DistrictDto>>() {}

            restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                typeReference
            ).body!!
        }

    companion object {
        private val logger = LoggerFactory.getLogger(RemoteTownService::class.java)
    }

    override fun getTownPage(pageable: Pageable): Page<TownDto> =
        townCache[pageable]!!

    override fun getDistrictPage(townExternalId: String, pageable: Pageable): Page<DistrictDto> =
        districtCache[CacheKey(townExternalId, pageable)]!!

    private data class CacheKey(
        val townExternalId: String,
        val pageable: Pageable
    )
}
