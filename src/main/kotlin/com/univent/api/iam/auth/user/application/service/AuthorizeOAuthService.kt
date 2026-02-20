package com.univent.api.iam.auth.user.application.service

import com.univent.api.iam.auth.core.infrastructure.oauth.OAuthProviderFactory
import com.univent.api.iam.auth.user.application.AuthorizeOAuthUseCase
import com.univent.api.iam.auth.user.application.command.AuthorizeOAuthCommand
import com.univent.api.iam.auth.user.application.result.AuthorizeOAuthResult
import org.springframework.stereotype.Service
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Service
class AuthorizeOAuthService(
    private val oAuthProviderFactory: OAuthProviderFactory
) : AuthorizeOAuthUseCase {

    override fun execute(command: AuthorizeOAuthCommand): AuthorizeOAuthResult {
        val (oAuthProviderType, redirectUrl) = command

        // 1. 해당되는 Provider(Kakao 등) 가져오기
        val provider = oAuthProviderFactory.getProvider(oAuthProviderType)

        // 2. Redirect URL 인코딩 (state 파라미터로 사용)
        val encodedState = redirectUrl?.let {
            URLEncoder.encode(it, StandardCharsets.UTF_8.name())
        } ?: ""

        // 3. 각 Provider가 정의한 인가 URL 생성
        val authUrl = provider.getAuthorizationUrl(encodedState)

        return AuthorizeOAuthResult(authUrl = authUrl)
    }
}
