package com.univent.api.iam.auth.organization.application

import com.univent.api.iam.auth.organization.application.command.ChangeOrganizationPasswordCommand

interface ChangeOrganizationPasswordUseCase {
    fun execute(command: ChangeOrganizationPasswordCommand)
}
