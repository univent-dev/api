package com.univent.api.iam.auth.user.application

import com.univent.api.iam.auth.user.application.command.UnlinkOAuthCommand

interface UnlinkOAuthUseCase {
    fun execute(command: UnlinkOAuthCommand)
}
