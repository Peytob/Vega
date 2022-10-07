package ru.vega.telegram.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.cache.annotation.Cacheable
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import ru.vega.model.dto.discipline.DisciplinesSetDto
import ru.vega.model.dto.university.UniversitySpecialityDto
import ru.vega.model.utils.Page
import ru.vega.model.utils.Pageable

@Service
class RemoteUniversitySpecialityService(
    @Qualifier("backendRestTemplate")
    private val restTemplate: RestTemplate
) : UniversitySpecialityService {

    companion object {
        private val logger = LoggerFactory.getLogger(RemoteUniversitySpecialityService::class.java)
    }

    @Cacheable("UniversitySpecialitiesByDisciplineSetAndScore")
    override fun getByDisciplinesSet(disciplinesSet: DisciplinesSetDto, score: Int?, pageable: Pageable) : Page<UniversitySpecialityDto> {
        logger.info("Updating university specialities of disciplines set with external id $disciplinesSet with score " +
                "$score for page $pageable")

        val uri = UriComponentsBuilder
            .fromUriString("/disciplinesSet/{disciplineSetId}/specialities")
            .queryParam("scoreFilter", score ?: Int.MAX_VALUE)
            .queryParam("page", pageable.page)
            .queryParam("size", pageable.size)
            .buildAndExpand(disciplinesSet.externalId)
            .toUriString()

        val typeReference = object : ParameterizedTypeReference<Page<UniversitySpecialityDto>>() {}

        return restTemplate.exchange(
            uri,
            HttpMethod.GET,
            null,
            typeReference
        ).body!!
    }

    @Cacheable("UniversitySpecialitiesByExternalId")
    override fun getByExternalId(externalId: String): UniversitySpecialityDto? {
        logger.info("Updating university speciality with external id {} from remote", externalId)

        return restTemplate
            .getForObject("/universitySpeciality/$externalId", UniversitySpecialityDto::class.java)
    }
}
