package com.univent.api.iam.auth.organization.application

import com.univent.api.iam.auth.organization.application.command.RegisterOrganizationCommand

interface RegisterOrganizationUseCase {
    fun execute(command: RegisterOrganizationCommand)
}
