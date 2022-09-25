package ru.vega.telegram.service

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.LoadingCache
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import ru.vega.model.dto.discipline.DisciplinesSetDto
import ru.vega.model.dto.university.UniversitySpecialityDto
import ru.vega.model.utils.Page
import ru.vega.model.utils.Pageable
import ru.vega.telegram.configuration.CacheProperties

@Service
class RemoteUniversitySpecialityService(
    private val restTemplate: RestTemplate,
    cacheProperties: CacheProperties
) : UniversitySpecialityService {

    private val disciplinesSetCache: LoadingCache<CacheKey, Page<UniversitySpecialityDto>> = Caffeine.newBuilder()
        .initialCapacity(100)
        .expireAfterAccess(cacheProperties.backendData)
        .build {

            val uri = UriComponentsBuilder
                .fromUriString("/disciplinesSet/{disciplineSetId}/specialities")
                .queryParam("page", it.pageable.page)
                .queryParam("size", it.pageable.size)
                .buildAndExpand(it.disciplinesSetId)
                .toUriString()

            val typeReference = object : ParameterizedTypeReference<Page<UniversitySpecialityDto>>() {}

            val page = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                typeReference
            ).body!!

            idsCache.putAll(
                page.content
                    .associateBy(UniversitySpecialityDto::externalId)
            )

            page
        }

    private val idsCache: Cache<String, UniversitySpecialityDto> = Caffeine.newBuilder()
        .initialCapacity(100)
        .expireAfterAccess(cacheProperties.backendData)
        .build()

    override fun getByDisciplinesSet(disciplinesSet: DisciplinesSetDto, pageable: Pageable): Page<UniversitySpecialityDto> =
        disciplinesSetCache[CacheKey(disciplinesSet.externalId, pageable)]!!

    override fun getByExternalId(externalId: String): UniversitySpecialityDto? =
        idsCache.getIfPresent(externalId)

    private data class CacheKey(
        val disciplinesSetId: String,
        val pageable: Pageable
    )
}
