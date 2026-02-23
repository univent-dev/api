package com.univent.api.iam.organization.application

import com.univent.api.iam.organization.application.command.DeleteOrganizationCommand

interface DeleteOrganizationUseCase {
    fun execute(command: DeleteOrganizationCommand)
}
