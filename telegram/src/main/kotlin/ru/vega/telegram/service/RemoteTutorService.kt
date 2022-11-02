package ru.vega.telegram.service

import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.Cacheable
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import ru.vega.model.dto.tutor.TutorDto
import ru.vega.model.utils.Page
import ru.vega.model.utils.Pageable
import java.util.*

@Component
class RemoteTutorService(
    private val restTemplate: RestTemplate
) : TutorService {

    companion object {
        private val logger = LoggerFactory.getLogger(RemoteTutorService::class.java)
    }

    @Cacheable("Tutors")
    override fun getTutorById(tutorId: UUID): TutorDto? {
        logger.info("Updating tutor with external id {} from remote", tutorId)

        return restTemplate
            .getForObject("/tutor/{tutorId}", TutorDto::class.java, tutorId)
    }

    @Cacheable("OnlineTutorsByDiscipline")
    override fun getOnlineTutorsByDiscipline(disciplineId: UUID, pageable: Pageable): Page<TutorDto> {
        logger.info("Updating available online tutors for page $pageable")

        val uri = UriComponentsBuilder
            .fromUriString("/tutor/search/online")
            .queryParam("page", pageable.page)
            .queryParam("size", pageable.size)
            .queryParam("disciplineId", disciplineId)
            .toUriString()

        val typeReference = object : ParameterizedTypeReference<Page<TutorDto>>() {}

        return restTemplate.exchange(
            uri,
            HttpMethod.GET,
            null,
            typeReference
        ).body!!
    }

    @Cacheable("TutorsPagesByDisciplineAndDistrict")
    override fun getTutorsByDisciplineAndDistrict(disciplineId: UUID, districtId: UUID, pageable: Pageable): Page<TutorDto> {
        logger.info("Updating available tutors in district with id $disciplineId and discipline $districtId for page $pageable")

        val uri = UriComponentsBuilder
            .fromUriString("/tutor/search")
            .queryParam("page", pageable.page)
            .queryParam("size", pageable.size)
            .queryParam("districtId", districtId)
            .queryParam("disciplineId", disciplineId)
            .toUriString()

        val typeReference = object : ParameterizedTypeReference<Page<TutorDto>>() {}

        return restTemplate.exchange(
            uri,
            HttpMethod.GET,
            null,
            typeReference
        ).body!!
    }
}