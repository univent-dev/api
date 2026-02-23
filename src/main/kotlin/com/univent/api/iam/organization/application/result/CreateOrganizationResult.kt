package com.univent.api.iam.organization.application.result

import com.univent.api.iam.organization.domain.OrganizationId

data class CreateOrganizationResult(
    val organizationId: OrganizationId
)
