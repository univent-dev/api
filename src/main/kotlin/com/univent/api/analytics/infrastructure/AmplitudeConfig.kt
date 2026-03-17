package com.univent.api.analytics.infrastructure

import com.amplitude.Amplitude
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AmplitudeConfig(
    private val properties: AmplitudeProperties
) {
    @Bean
    fun amplitudeClient(): Amplitude {
        return Amplitude.getInstance().apply {
            init(properties.apiKey)

        }
    }
}
