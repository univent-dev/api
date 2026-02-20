package com.univent.api.iam.auth.user.application.service

import com.univent.api.common.core.domain.DomainEventPublisher
import com.univent.api.common.exception.CustomException
import com.univent.api.config.security.AccountRole
import com.univent.api.config.security.JwtProvider
import com.univent.api.iam.auth.core.domain.AuthErrorCode
import com.univent.api.iam.auth.user.application.RenewTokenUseCase
import com.univent.api.iam.auth.user.application.command.RenewTokenCommand
import com.univent.api.iam.auth.user.application.result.RenewTokenResult
import com.univent.api.iam.auth.user.domain.AuthUserStore
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RenewTokenService(
    private val jwtProvider: JwtProvider,
    private val authUserStore: AuthUserStore,
    private val domainEventPublisher: DomainEventPublisher
) : RenewTokenUseCase {

    @Transactional
    override fun execute(command: RenewTokenCommand): RenewTokenResult {
        val (userId, refreshToken) = command

        val authUser = authUserStore.loadByRefreshToken(refreshToken)
            ?: throw CustomException(AuthErrorCode.AUTH_INVALID_REFRESH_TOKEN)

        if (authUser.userId != userId) {
            throw CustomException(AuthErrorCode.AUTH_INVALID_REFRESH_TOKEN)
        }

        // 3. 새로운 토큰 쌍 생성 (Rotation 전략)
        val newAccessToken = jwtProvider.generateToken(
            sub = userId.value.toString(),
            roles = listOf(AccountRole.USER),
            isAccessToken = true
        )
        val newRefreshToken = jwtProvider.generateToken(
            sub = userId.value.toString(),
            roles = listOf(AccountRole.USER),
            isAccessToken = false
        )

        authUser.updateRefreshToken(newRefreshToken)
        authUserStore.save(authUser)

        domainEventPublisher.publish(authUser)

        return RenewTokenResult(
            accessToken = newAccessToken,
            refreshToken = newRefreshToken
        )
    }
}
