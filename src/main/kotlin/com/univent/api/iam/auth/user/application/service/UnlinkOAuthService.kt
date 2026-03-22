package com.univent.api.iam.auth.user.application.service

import com.univent.api.common.exception.CustomException
import com.univent.api.iam.auth.core.domain.AuthErrorCode
import com.univent.api.iam.auth.core.infrastructure.oauth.OAuthProviderFactory
import com.univent.api.iam.auth.user.application.UnlinkOAuthUseCase
import com.univent.api.iam.auth.user.application.command.UnlinkOAuthCommand
import com.univent.api.iam.auth.user.domain.AuthUserStore
import com.univent.api.iam.user.UserApi
import com.univent.api.iam.user.UserDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UnlinkOAuthService(
    private val authUserStore: AuthUserStore,
    private val userApi: UserApi,
    private val oAuthProviderFactory: OAuthProviderFactory
): UnlinkOAuthUseCase {
    @Transactional
    override fun execute(command: UnlinkOAuthCommand) {
        val authUser = authUserStore.loadByUserId(command.userId)
            ?: throw CustomException(AuthErrorCode.AUTH_USER_NOT_FOUND)

        userApi.deleteUser(UserDto.DeleteUserRequest(authUser.userId.value))
        authUserStore.deleteById(authUser.id)

        val oAuthProvider = oAuthProviderFactory.getProvider(command.provider)
        oAuthProvider.unlinkAccount(authUser.oAuthId)

        authUser.delete()
    }
}
