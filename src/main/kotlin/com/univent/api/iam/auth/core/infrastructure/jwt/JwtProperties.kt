package com.univent.api.iam.auth.core.infrastructure.jwt

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "jwt")
data class JwtProperties(
    val access: TokenInfo,
    val refresh: TokenInfo
) {
    data class TokenInfo(
        val secret: String,
        val expiration: Long
    )
}
