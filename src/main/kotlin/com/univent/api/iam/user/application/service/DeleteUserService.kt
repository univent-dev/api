package com.univent.api.iam.user.application.service

import com.univent.api.common.exception.CustomException
import com.univent.api.iam.user.application.DeleteUserUseCase
import com.univent.api.iam.user.application.command.DeleteUserCommand
import com.univent.api.iam.user.domain.UserErrorCode
import com.univent.api.iam.user.domain.UserStore
import org.springframework.stereotype.Service

@Service
class DeleteUserService(
    private val userStore: UserStore
): DeleteUserUseCase {
    override fun execute(deleteUserCommand: DeleteUserCommand) {
        userStore.loadById(deleteUserCommand.id)
            ?: throw CustomException(UserErrorCode.USER_NOT_FOUND)

        userStore.deleteById(deleteUserCommand.id)
    }
}
