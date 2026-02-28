package com.univent.api.iam.auth.organization.application.command

import com.univent.api.iam.organization.domain.OrganizationId

data class ChangeOrganizationPasswordCommand(
    val organizationId: OrganizationId,
    val currentPassword: String,
    val newPassword: String
)
