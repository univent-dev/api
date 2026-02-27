package com.univent.api.iam.organization.application.command

import com.univent.api.iam.organization.OrganizationDto
import com.univent.api.iam.organization.domain.OrganizationId

data class DeleteOrganizationCommand(
    val organizationId: OrganizationId
) {
    companion object {
        fun fromDto(dto: OrganizationDto.DeleteOrganizationRequest): DeleteOrganizationCommand = DeleteOrganizationCommand(
            organizationId = OrganizationId(dto.id)
        )
    }
}
