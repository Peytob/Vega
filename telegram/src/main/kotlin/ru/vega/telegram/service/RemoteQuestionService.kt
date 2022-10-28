package ru.vega.telegram.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.Cacheable
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import ru.vega.model.dto.faq.QuestionDto
import ru.vega.model.utils.Page
import ru.vega.model.utils.Pageable

@Service
class RemoteQuestionService(
    private val restTemplate: RestTemplate
) : QuestionService {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(RemoteQuestionService::class.java)
    }

    @Cacheable("FaqPages")
    override fun get(pageable: Pageable): Page<QuestionDto> {
        logger.info("Updating available FAQ for page $pageable")

        val uri = UriComponentsBuilder
            .fromUriString("/faq")
            .queryParam("page", pageable.page)
            .queryParam("size", pageable.size)
            .toUriString()

        return restTemplate.exchange(
            uri,
            HttpMethod.GET,
            null,
            object : ParameterizedTypeReference<Page<QuestionDto>>() {}
        ).body!!
    }

    @Cacheable("Faqs")
    override fun get(externalId: String): QuestionDto? {
        logger.info("Updating FAQ with external id $externalId from remote")

        return restTemplate.exchange(
            "/faq/$externalId",
            HttpMethod.GET,
            null,
            QuestionDto::class.java
        ).body!!
    }
}