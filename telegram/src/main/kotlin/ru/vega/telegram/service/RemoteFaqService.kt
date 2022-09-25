package ru.vega.telegram.service

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.LoadingCache
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import ru.vega.model.dto.faq.QuestionDto
import ru.vega.model.utils.Page
import ru.vega.model.utils.Pageable
import ru.vega.telegram.configuration.CacheProperties

@Service
class RemoteFaqService(
    private val restTemplate: RestTemplate,
    cacheProperties: CacheProperties
) : FaqService {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(RemoteFaqService::class.java)
    }

    private val questionsCache: LoadingCache<Pageable, Page<QuestionDto>> = Caffeine.newBuilder()
        .initialCapacity(100)
        .expireAfterAccess(cacheProperties.backendData)
        .build {
            logger.info("Updating FAQ cache for $it")

            val uri = UriComponentsBuilder
                .fromUriString("/faq")
                .queryParam("page", it.page)
                .queryParam("size", it.size)
                .toUriString()

            val typeReference = object : ParameterizedTypeReference<Page<QuestionDto>>() {}

            val page = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                typeReference
            ).body!!

            idsCache.putAll(
                page.content
                    .associateBy(QuestionDto::externalId)
            )

            page
        }

    private val idsCache: Cache<String, QuestionDto> = Caffeine.newBuilder()
        .initialCapacity(100)
        .expireAfterAccess(cacheProperties.backendData)
        .build()

    override fun get(pageable: Pageable): Page<QuestionDto> = questionsCache[pageable]!!

    override fun get(externalId: String): QuestionDto? = idsCache.getIfPresent(externalId)
}