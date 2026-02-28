package com.univent.api.iam.auth.organization.application

import com.univent.api.iam.auth.organization.application.command.LoginCommand
import com.univent.api.iam.auth.organization.application.result.LoginResult

interface LoginUseCase {
    fun execute(command: LoginCommand): LoginResult
}
