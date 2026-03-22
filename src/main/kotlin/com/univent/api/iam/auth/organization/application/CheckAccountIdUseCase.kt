package com.univent.api.iam.auth.organization.application

import com.univent.api.iam.auth.organization.application.command.CheckAccountIdCommand

interface CheckAccountIdUseCase {
    fun execute(command: CheckAccountIdCommand): Boolean
}
