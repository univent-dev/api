package com.univent.api.iam.auth.user.application

import com.univent.api.iam.auth.user.application.command.LogoutCommand

interface LogoutUseCase {
    fun execute(command: LogoutCommand)
}
