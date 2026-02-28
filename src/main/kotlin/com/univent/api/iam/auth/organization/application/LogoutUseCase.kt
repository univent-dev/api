package com.univent.api.iam.auth.organization.application

import com.univent.api.iam.auth.organization.application.command.LogoutCommand

interface LogoutUseCase {
    fun execute(command: LogoutCommand)
}
