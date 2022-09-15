package ru.vega.telegram.configuration

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
@EnableConfigurationProperties(BackendProperties::class)
class RestTemplateConfiguration(
    val backendProperties: BackendProperties
) {

    @Bean("backendRestTemplate")
    fun restTemplate(restTemplateBuilder: RestTemplateBuilder): RestTemplate = restTemplateBuilder
            .rootUri(backendProperties.url.toString())
            .build()
}
