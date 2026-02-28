package com.univent.api.iam.auth.organization.application

import com.univent.api.iam.auth.organization.application.command.OrganizationLogoutCommand

interface OrganizationLogoutUseCase {
    fun execute(command: OrganizationLogoutCommand)
}
