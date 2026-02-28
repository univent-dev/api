package com.univent.api.iam.auth.organization.application.service

import com.univent.api.common.exception.CustomException
import com.univent.api.iam.auth.core.domain.AuthErrorCode
import com.univent.api.iam.auth.organization.application.OrganizationLogoutUseCase
import com.univent.api.iam.auth.organization.application.command.OrganizationLogoutCommand
import com.univent.api.iam.auth.organization.domain.AuthOrganizationStore
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrganizationOrganizationLogoutService(
    private val authOrganizationStore: AuthOrganizationStore
): OrganizationLogoutUseCase {
    @Transactional
    override fun execute(command: OrganizationLogoutCommand) {
        val authOrganization = authOrganizationStore.findByOrganizationId(command.organizationId)
            ?: throw CustomException(AuthErrorCode.AUTH_ORGANIZATION_INVALID_ACCESS_TOKEN)

        authOrganization.updateRefreshToken(null)

        authOrganizationStore.update(authOrganization)
    }
}
