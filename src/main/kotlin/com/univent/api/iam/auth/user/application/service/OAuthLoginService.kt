package com.univent.api.iam.auth.user.application.service

import com.univent.api.common.core.domain.DomainEventPublisher
import com.univent.api.common.core.domain.IdGenerator
import com.univent.api.config.security.AccountRole
import com.univent.api.config.security.JwtProvider
import com.univent.api.iam.auth.core.domain.OAuthUser
import com.univent.api.iam.auth.core.infrastructure.oauth.OAuthProviderFactory
import com.univent.api.iam.auth.user.application.OAuthLoginUseCase
import com.univent.api.iam.auth.user.application.command.OAuthLoginCommand
import com.univent.api.iam.auth.user.application.result.OAuthLoginResult
import com.univent.api.iam.auth.user.domain.AuthUser
import com.univent.api.iam.auth.user.domain.AuthUserId
import com.univent.api.iam.auth.user.domain.AuthUserStore
import com.univent.api.iam.auth.core.domain.OAuthProviderType
import com.univent.api.iam.user.UserApi
import com.univent.api.iam.user.UserDto
import com.univent.api.iam.user.domain.UserId
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Service
class OAuthLoginService(
    private val authUserStore: AuthUserStore,
    private val userApi: UserApi,
    private val oAuthProviderFactory: OAuthProviderFactory,
    private val jwtProvider: JwtProvider,
    private val idGenerator: IdGenerator,
    private val domainEventPublisher: DomainEventPublisher
): OAuthLoginUseCase {
    private val logger = KotlinLogging.logger {}

    @Transactional
    override fun execute(command: OAuthLoginCommand): OAuthLoginResult {
        val (oAuthProviderType, code, state) = command

        // 1. 소셜 로그인 유저 정보 가져오기
        val oAuthInfo = getOAuthUserInfo(oAuthProviderType, code)

        // 2. AuthUser 조회 또는 생성
        val authUser = findOrCreateAuth(oAuthInfo.oauthId, oAuthProviderType, oAuthInfo.email)

        // 3. 토큰 발행 및 리프레시 토큰 업데이트
        val (accessToken, refreshToken) = generateAndSaveTokens(authUser)

        // 4. 리다이렉트 URL 복호화
        val redirectUrl = decodeRedirectUrl(state)

        return OAuthLoginResult(
            accessToken = accessToken,
            refreshToken = refreshToken,
            userId = authUser.userId,
            redirectUrl = redirectUrl
        )
    }

    private fun getOAuthUserInfo(type: OAuthProviderType, code: String): OAuthUser {
        val provider = oAuthProviderFactory.getProvider(type)
        val token = provider.getToken(code)
        return provider.getUserInfo(token)
    }

    private fun findOrCreateAuth(oauthId: String, provider: OAuthProviderType, email: String): AuthUser {
        return authUserStore.loadByOAuthIdAndProvider(oauthId, provider.value)
            ?: createNewAuthProcess(oauthId, provider, email)
    }

    private fun createNewAuthProcess(oauthId: String, provider: OAuthProviderType, email: String): AuthUser {
        val userId = createNewUser(email)


        logger.info { "Getting OAuth user info for provider: $provider" }

        val authUser = AuthUser.create(
            id = AuthUserId(idGenerator.generateId()),
            oAuthId = oauthId,
            provider = provider,
            userId = UserId(userId)
        )

        authUserStore.save(authUser)

        domainEventPublisher.publish(authUser)

        return authUser
    }

    private fun createNewUser(email: String): Long {
        return userApi.createUser(UserDto.CreateUserRequest(email)).id
    }

    private fun generateAndSaveTokens(authUser: AuthUser): Pair<String, String> {
        val accessToken = jwtProvider.generateToken(
            sub = authUser.userId.value.toString(),
            roles = listOf(AccountRole.USER),
            isAccessToken = true
        )
        val refreshToken = jwtProvider.generateToken(
            sub = authUser.userId.value.toString(),
            roles = listOf(AccountRole.USER),
            isAccessToken = false
        )

        authUser.updateRefreshToken(refreshToken)
        authUserStore.save(authUser)

        return Pair(accessToken, refreshToken)
    }

    private fun decodeRedirectUrl(state: String?): String {
        return state?.let { URLDecoder.decode(it, StandardCharsets.UTF_8.name()) } ?: ""
    }
}
