package ru.vega.telegram.service

import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate

fun <T> RestTemplate.getForObjectOrNull(url: String, responseType: Class<T>, vararg uriVariables: Any?): T? {
    return try {
        getForObject(url, responseType, *uriVariables)
    } catch (notFound: HttpClientErrorException.NotFound) {
        null
    }
}