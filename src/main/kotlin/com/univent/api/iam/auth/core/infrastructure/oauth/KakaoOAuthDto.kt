package com.univent.api.iam.auth.core.infrastructure.oauth

import com.fasterxml.jackson.annotation.JsonProperty

sealed class KakaoOAuthDto {
    /**
     * 유저 정보 응답 (https://kapi.kakao.com/v2/user/me)
     */
    data class UserRes(
        val id: Long,
        @JsonProperty("kakao_account")
        val kakaoAccount: KakaoAccount?
    ) : KakaoOAuthDto() {

        data class KakaoAccount(
            val email: String?,
            @JsonProperty("is_email_valid")
            val isEmailValid: Boolean?,
            @JsonProperty("is_email_verified")
            val isEmailVerified: Boolean?
        )
    }

    /**
     * 토큰 발급 응답 (https://kauth.kakao.com/oauth/token)
     */
    data class TokenRes(
        @JsonProperty("access_token")
        val accessToken: String,
        @JsonProperty("token_type")
        val tokenType: String,
        @JsonProperty("refresh_token")
        val refreshToken: String?,
        @JsonProperty("expires_in")
        val expiresIn: Int,
        val scope: String?
    ) : KakaoOAuthDto()
}
