package com.univent.api.iam.organization.application

import com.univent.api.iam.organization.OrganizationApi
import com.univent.api.iam.organization.OrganizationDto
import com.univent.api.iam.organization.application.command.CreateOrganizationCommand
import com.univent.api.iam.organization.application.command.DeleteOrganizationCommand
import org.springframework.stereotype.Service

@Service
class OrganizationApiImpl(
    private val createOrganizationUseCase: CreateOrganizationUseCase,
    private val deleteOrganizationUseCase: DeleteOrganizationUseCase
): OrganizationApi {
    override fun createOrganization(request: OrganizationDto.CreateOrganizationRequest): OrganizationDto.CreateOrganizationResponse {
        val command = CreateOrganizationCommand.fromDto(request)
        val result = createOrganizationUseCase.execute(command)

        return result.toDto()
    }

    override fun deleteOrganization(request: OrganizationDto.DeleteOrganizationRequest) {
        val command = DeleteOrganizationCommand.fromDto(request)
        deleteOrganizationUseCase.execute(command)
    }
}
