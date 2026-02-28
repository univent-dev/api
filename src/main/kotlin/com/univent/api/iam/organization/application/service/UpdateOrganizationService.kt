package com.univent.api.iam.organization.application.service

import com.univent.api.common.exception.CustomException
import com.univent.api.iam.organization.application.UpdateOrganizationUseCase
import com.univent.api.iam.organization.application.command.UpdateOrganizationCommand
import com.univent.api.iam.organization.domain.OrganizationErrorCode
import com.univent.api.iam.organization.domain.OrganizationId
import com.univent.api.iam.organization.domain.OrganizationStore
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class UpdateOrganizationService(
    private val organizationStore: OrganizationStore
) : UpdateOrganizationUseCase {

    @Transactional
    override fun execute(command: UpdateOrganizationCommand) {
        val organization = organizationStore.loadById(command.organizationId)
            ?: throw CustomException(OrganizationErrorCode.ORGANIZATION_NOT_FOUND)

        organization.update(
            newName = command.name,
            newContact = command.contact
        )

        organizationStore.update(organization)
    }
}
