package com.univent.api.iam.auth.organization.application

import com.univent.api.iam.auth.organization.application.command.RenewOrganizationTokenCommand
import com.univent.api.iam.auth.organization.application.result.RenewOrganizationTokenResult

interface RenewOrganizationTokenUseCase {
    fun execute(command: RenewOrganizationTokenCommand): RenewOrganizationTokenResult
}