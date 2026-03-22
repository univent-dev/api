package com.univent.api.iam.auth.core.infrastructure.oauth

import com.univent.api.common.exception.CustomException
import com.univent.api.iam.auth.core.domain.AuthErrorCode
import com.univent.api.iam.auth.core.domain.OAuthProvider
import com.univent.api.iam.auth.core.domain.OAuthUser
import com.univent.api.iam.auth.core.domain.OAuthProviderType
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestClient
import org.springframework.web.client.body
import org.springframework.web.util.UriComponentsBuilder
import java.util.Optional

@Component
class KakaoOAuthProvider(
    private val properties: KakaoOAuthProperties,
    @Value("\${kakao.admin-key}") private val adminKey: String
) : OAuthProvider {

    private val restClient = RestClient.create()

    private val clientId = properties.registration.kakao.clientId
    private val redirectUri = properties.registration.kakao.redirectUri

    override fun getToken(code: String): String {
        val response = restClient.post()
            .uri("https://kauth.kakao.com/oauth/token")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(LinkedMultiValueMap<String, String>().apply {
                add("grant_type", "authorization_code")
                add("client_id", clientId)
                add("redirect_uri", redirectUri)
                add("code", code)
            })
            .retrieve()
            .onStatus({ it.isError }, { _, res -> throw CustomException(AuthErrorCode.AUTH_INVALID_OAUTH_TOKEN) })
            .body(Map::class.java)

        return response?.get("access_token") as? String
            ?: throw CustomException(AuthErrorCode.AUTH_INVALID_OAUTH_TOKEN)
    }

    override fun getUserInfo(token: String): OAuthUser {
        val response = restClient.get()
            .uri("https://kapi.kakao.com/v2/user/me")
            .header("Authorization", "Bearer $token")
            .retrieve()
            .body<KakaoOAuthDto.UserRes>()
            ?: throw CustomException(AuthErrorCode.AUTH_OAUTH_USER_NOT_FOUND)

        val email = response.kakaoAccount?.email
            ?: throw CustomException(AuthErrorCode.AUTH_OAUTH_USER_EMAIL_NOT_FOUND)

        return OAuthUser(
            oauthId = response.id.toString(),
            provider = OAuthProviderType.KAKAO,
            email = email
        )
    }

    override fun getAuthorizationUrl(state: String?): String {
        return UriComponentsBuilder.fromUriString("https://kauth.kakao.com/oauth/authorize")
            .queryParam("response_type", "code")
            .queryParam("client_id", clientId)
            .queryParam("redirect_uri", redirectUri)
            .queryParamIfPresent("state", Optional.ofNullable(state))
            .build().toUriString()
    }

    override fun unlinkAccount(oAuthId: String) {
        restClient.post()
            .uri("https://kapi.kakao.com/v1/user/unlink")
            .header("Authorization", "KakaoAK $adminKey")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body("target_id_type=user_id&target_id=$oAuthId")
            .retrieve()
            .toBodilessEntity()
    }
}
