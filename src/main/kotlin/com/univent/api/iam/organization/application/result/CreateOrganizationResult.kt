package com.univent.api.iam.organization.application.result

import com.univent.api.iam.organization.OrganizationDto
import com.univent.api.iam.organization.domain.OrganizationId

data class CreateOrganizationResult(
    val organizationId: OrganizationId
) {
    fun toDto(): OrganizationDto.CreateOrganizationResponse = OrganizationDto.CreateOrganizationResponse(
        id = organizationId.value
    )
}
