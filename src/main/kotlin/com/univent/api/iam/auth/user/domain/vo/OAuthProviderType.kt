package com.univent.api.iam.auth.user.domain.vo

import com.univent.api.common.exception.CustomException
import com.univent.api.iam.auth.core.domain.AuthErrorCode

enum class OAuthProviderType(val value: String) {
    KAKAO("KAKAO"),
    GOOGLE("NAVER"),
    APPLE("APPLE"),
    NAVER("NAVER");

    companion object {
        fun from(value: String): OAuthProviderType {
            return entries.find { it.value == value }
                ?: throw CustomException(AuthErrorCode.AUTH_INVALID_OAUTH_PROVIDER)
        }
    }
}
