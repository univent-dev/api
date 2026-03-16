package com.univent.api.analytics.infrastructure

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "amplitude")
data class AmplitudeProperties(
    val apiKey: String,
    val secretKey: String
)
