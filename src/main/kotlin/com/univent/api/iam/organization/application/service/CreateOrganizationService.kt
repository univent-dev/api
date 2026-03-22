package com.univent.api.iam.organization.application.service

import com.univent.api.common.core.domain.IdGenerator
import com.univent.api.iam.organization.application.CreateOrganizationUseCase
import com.univent.api.iam.organization.application.command.CreateOrganizationCommand
import com.univent.api.iam.organization.application.result.CreateOrganizationResult
import com.univent.api.iam.organization.domain.Organization
import com.univent.api.iam.organization.domain.OrganizationId
import com.univent.api.iam.organization.domain.OrganizationStore
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class CreateOrganizationService(
    private val organizationStore: OrganizationStore,
    private val idGenerator: IdGenerator
): CreateOrganizationUseCase {
    @Transactional
    override fun execute(command: CreateOrganizationCommand): CreateOrganizationResult {
        val organization = Organization.create(
            id = OrganizationId(idGenerator.generateId()),
            name = command.name,
            contact = command.contact
        )

        organizationStore.save(organization)

        return CreateOrganizationResult(organization.id)
    }
}
