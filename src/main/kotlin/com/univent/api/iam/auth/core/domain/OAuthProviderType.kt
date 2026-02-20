package com.univent.api.iam.auth.core.domain

import com.univent.api.common.exception.CustomException

enum class OAuthProviderType(val value: String) {
    KAKAO("kakao"),
    GOOGLE("google"),
    APPLE("apple"),
    NAVER("naver");

    companion object {
        fun from(value: String): OAuthProviderType {
            return entries.find { it.value == value }
                ?: throw CustomException(AuthErrorCode.AUTH_INVALID_OAUTH_PROVIDER)
        }
    }
}
