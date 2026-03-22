package com.univent.api.iam.user.application

import com.univent.api.iam.user.application.command.CreateUserCommand
import com.univent.api.iam.user.application.result.CreateUserResult

interface CreateUserUseCase {
    fun execute(command: CreateUserCommand): CreateUserResult
}
