package com.univent.api.iam.auth.organization.application.command

import com.univent.api.iam.organization.domain.OrganizationId

data class OrganizationLogoutCommand(
    val organizationId: OrganizationId
)
