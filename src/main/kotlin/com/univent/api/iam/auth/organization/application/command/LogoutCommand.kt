package com.univent.api.iam.auth.organization.application.command

import com.univent.api.iam.organization.domain.OrganizationId

data class LogoutCommand(
    val organizationId: OrganizationId
)
