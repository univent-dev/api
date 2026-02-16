package com.univent.api.iam.user.application

import com.univent.api.iam.user.application.command.DeleteUserCommand

interface DeleteUserUseCase {
    fun execute(deleteUserCommand: DeleteUserCommand)
}
