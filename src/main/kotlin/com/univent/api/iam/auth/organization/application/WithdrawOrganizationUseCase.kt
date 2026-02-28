package com.univent.api.iam.auth.organization.application

import com.univent.api.iam.auth.organization.application.command.WithdrawOrganizationCommand

interface WithdrawOrganizationUseCase {
    fun execute(command: WithdrawOrganizationCommand)
}
