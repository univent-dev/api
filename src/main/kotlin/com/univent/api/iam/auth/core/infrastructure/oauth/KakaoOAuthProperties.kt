package com.univent.api.iam.auth.core.infrastructure.oauth

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "spring.security.oauth2.client")
data class KakaoOAuthProperties(
    val registration: Registration,
    val provider: Provider
) {
    data class Registration(
        val kakao: KakaoRegistration
    )

    data class KakaoRegistration(
        val clientId: String,
        val redirectUri: String,
        val scope: List<String>
    )

    data class Provider(
        val kakao: KakaoProviderInfo
    )

    data class KakaoProviderInfo(
        val authorizationUri: String,
        val tokenUri: String,
        val userInfoUri: String
    )
}
