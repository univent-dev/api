package com.univent.api.iam.organization.application

import com.univent.api.iam.organization.application.command.UpdateOrganizationCommand

interface UpdateOrganizationUseCase {
    fun execute(command: UpdateOrganizationCommand)
}
