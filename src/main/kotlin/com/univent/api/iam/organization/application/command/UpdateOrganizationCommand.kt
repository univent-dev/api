package com.univent.api.iam.organization.application.command

import com.univent.api.iam.organization.domain.OrganizationId

data class UpdateOrganizationCommand(
    val organizationId: OrganizationId,
    val name: String?,
    val contact: String?
)
