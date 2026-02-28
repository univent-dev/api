package com.univent.api.iam.auth.organization.application

import com.univent.api.iam.auth.organization.application.command.OrganizationLoginCommand
import com.univent.api.iam.auth.organization.application.result.LoginResult

interface OrganizationLoginUseCase {
    fun execute(command: OrganizationLoginCommand): LoginResult
}
