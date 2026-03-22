package com.univent.api.iam.auth.user.application

import com.univent.api.iam.auth.user.application.command.AuthorizeOAuthCommand
import com.univent.api.iam.auth.user.application.result.AuthorizeOAuthResult

interface AuthorizeOAuthUseCase {
    fun execute(command: AuthorizeOAuthCommand): AuthorizeOAuthResult
}
