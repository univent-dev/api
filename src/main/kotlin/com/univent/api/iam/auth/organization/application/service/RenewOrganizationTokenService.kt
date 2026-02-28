package com.univent.api.iam.auth.organization.application.service

import com.univent.api.common.core.domain.vo.AccountRole
import com.univent.api.common.exception.CustomException
import com.univent.api.iam.JwtApi
import com.univent.api.iam.auth.core.domain.AuthErrorCode
import com.univent.api.iam.auth.organization.application.RenewOrganizationTokenUseCase
import com.univent.api.iam.auth.organization.application.command.RenewOrganizationTokenCommand
import com.univent.api.iam.auth.organization.application.result.RenewOrganizationTokenResult
import com.univent.api.iam.auth.organization.domain.AuthOrganization
import com.univent.api.iam.auth.organization.domain.AuthOrganizationStore
import com.univent.api.iam.organization.domain.OrganizationId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RenewOrganizationTokenService(
    private val authOrganizationStore: AuthOrganizationStore,
    private val jwtApi: JwtApi
) : RenewOrganizationTokenUseCase {

    @Transactional
    override fun execute(command: RenewOrganizationTokenCommand): RenewOrganizationTokenResult {
        val authOrganization = validateRefreshToken(
            organizationId = command.organizationId,
            refreshToken = command.refreshToken
        )

        val organizationIdValue = authOrganization.organizationId.value
        val roles = listOf(AccountRole.ORGANIZATION)

        val newAccessToken = jwtApi.createAccessToken(organizationIdValue, roles)
        val newRefreshToken = jwtApi.createRefreshToken(organizationIdValue, roles)

        authOrganization.updateRefreshToken(newRefreshToken)
        authOrganizationStore.update(authOrganization)

        return RenewOrganizationTokenResult(
            accessToken = newAccessToken,
            refreshToken = newRefreshToken
        )
    }

    private fun validateRefreshToken(
        organizationId: OrganizationId,
        refreshToken: String
    ): AuthOrganization {
        val authOrganization = authOrganizationStore.findByRefreshToken(refreshToken)
            ?: throw CustomException(AuthErrorCode.AUTH_INVALID_REFRESH_TOKEN)

        if (authOrganization.organizationId != organizationId) {
            throw CustomException(AuthErrorCode.AUTH_INVALID_REFRESH_TOKEN)
        }

        if (authOrganization.isDeleted) {
            throw CustomException(AuthErrorCode.AUTH_ORGANIZATION_NOT_FOUND)
        }

        return authOrganization
    }
}
