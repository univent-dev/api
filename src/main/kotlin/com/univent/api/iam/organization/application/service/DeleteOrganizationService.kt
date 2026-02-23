package com.univent.api.iam.organization.application.service

import com.univent.api.common.exception.CustomException
import com.univent.api.iam.organization.application.DeleteOrganizationUseCase
import com.univent.api.iam.organization.application.command.DeleteOrganizationCommand
import com.univent.api.iam.organization.domain.OrganizationErrorCode
import com.univent.api.iam.organization.domain.OrganizationId
import com.univent.api.iam.organization.domain.OrganizationStore
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class DeleteOrganizationService(
    private val organizationStore: OrganizationStore
) : DeleteOrganizationUseCase {

    @Transactional
    override fun execute(command: DeleteOrganizationCommand) {
        val organization = organizationStore.loadById(command.organizationId)
            ?: throw CustomException(OrganizationErrorCode.ORGANIZATION_NOT_FOUND)

        organization.delete()

        organizationStore.deleteById(command.organizationId)
    }
}
