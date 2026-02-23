package com.univent.api.iam.organization.application.command

import com.univent.api.iam.organization.domain.OrganizationId

data class DeleteOrganizationCommand(
    val organizationId: OrganizationId
)
