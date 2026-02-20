package com.univent.api.iam.auth.user.application

import com.univent.api.iam.auth.user.application.command.OAuthLoginCommand
import com.univent.api.iam.auth.user.application.result.OAuthLoginResult

interface OAuthLoginUseCase {
    fun execute(command: OAuthLoginCommand): OAuthLoginResult
}
