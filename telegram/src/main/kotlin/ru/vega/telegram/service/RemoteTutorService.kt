package ru.vega.telegram.service

import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.LoadingCache
import org.slf4j.LoggerFactory
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import ru.vega.model.dto.tutor.TutorDto
import ru.vega.model.utils.Page
import ru.vega.model.utils.Pageable
import ru.vega.telegram.configuration.CacheProperties

@Component
class RemoteTutorService(
    private val restTemplate: RestTemplate,
    cacheProperties: CacheProperties
) : TutorService {

    companion object {
        private val logger = LoggerFactory.getLogger(RemoteTutorService::class.java)
    }

    private val cache: LoadingCache<CacheKey, Page<TutorDto>> = Caffeine.newBuilder()
        .initialCapacity(100)
        .expireAfterAccess(cacheProperties.session)
        .build {
            logger.info("Updating tutor cache for request $it")

            val uri = UriComponentsBuilder
                .fromUriString("/tutor/search")
                .queryParam("page", it.pageable.page)
                .queryParam("size", it.pageable.size)
                .queryParam("districtId", it.districtExternalId)
                .queryParam("disciplineId", it.disciplineExternalId)
                .toUriString()

            val typeReference = object : ParameterizedTypeReference<Page<TutorDto>>() {}

            restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                typeReference
            ).body!!
        }

    override fun getTutorsByDisciplineAndDistrict(
        disciplineExternalId: String,
        districtExternalId: String,
        pageable: Pageable
    ): Page<TutorDto> =
        cache[CacheKey(disciplineExternalId, districtExternalId, pageable)]!!

    private data class CacheKey(
        val disciplineExternalId: String,
        val districtExternalId: String,
        val pageable: Pageable
    )
}