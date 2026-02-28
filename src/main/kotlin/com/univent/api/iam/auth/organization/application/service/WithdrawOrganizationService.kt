package com.univent.api.iam.auth.organization.application.service

import com.univent.api.common.exception.CustomException
import com.univent.api.iam.auth.core.domain.AuthErrorCode
import com.univent.api.iam.auth.organization.application.WithdrawOrganizationUseCase
import com.univent.api.iam.auth.organization.application.command.WithdrawOrganizationCommand
import com.univent.api.iam.auth.organization.domain.AuthOrganizationStore
import com.univent.api.iam.organization.OrganizationApi
import com.univent.api.iam.organization.OrganizationDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class WithdrawOrganizationService(
    private val authOrganizationStore: AuthOrganizationStore,
    private val organizationApi: OrganizationApi
): WithdrawOrganizationUseCase {
    @Transactional
    override fun execute(command: WithdrawOrganizationCommand) {
        val organizationId = command.organizationId

        authOrganizationStore.deleteByOrganizationId(organizationId)

        try {
            organizationApi.deleteOrganization(
                OrganizationDto.DeleteOrganizationRequest(id = organizationId.value)
            )
        } catch (e: Exception) {
            throw CustomException(AuthErrorCode.AUTH_ORGANIZATION_WITHDRAW_FAILED)
        }
    }
}
