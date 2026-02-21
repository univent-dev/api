package com.univent.api.iam.auth.user.application

import com.univent.api.iam.auth.user.application.command.RenewTokenCommand
import com.univent.api.iam.auth.user.application.result.RenewTokenResult

interface RenewTokenUseCase {
    fun execute(command: RenewTokenCommand): RenewTokenResult
}
