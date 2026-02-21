package com.univent.api.iam.auth.user.application.service

import com.univent.api.common.exception.CustomException
import com.univent.api.iam.auth.core.domain.AuthErrorCode
import com.univent.api.iam.auth.user.application.LogoutUseCase
import com.univent.api.iam.auth.user.application.command.LogoutCommand
import com.univent.api.iam.auth.user.domain.AuthUserStore
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LogoutService(
    private val authUserStore: AuthUserStore
) : LogoutUseCase {

    @Transactional
    override fun execute(command: LogoutCommand) {
        val authUser = authUserStore.loadByUserId(command.userId)
            ?: throw CustomException(AuthErrorCode.AUTH_USER_NOT_FOUND)

        authUser.updateRefreshToken(null)

        authUserStore.save(authUser)
    }
}
