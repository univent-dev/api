package com.univent.api.iam.user.application

import com.univent.api.iam.user.UserApi
import com.univent.api.iam.user.UserDto
import com.univent.api.iam.user.application.command.CreateUserCommand
import com.univent.api.iam.user.application.command.DeleteUserCommand
import org.springframework.stereotype.Service

@Service
class UserApiImpl(
    private val createUserUseCase: CreateUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase
): UserApi {
    override fun createUser(req: UserDto.CreateUserRequest): UserDto.CreateUserResponse {
        val command = CreateUserCommand.fromDto(req)
        val result = createUserUseCase.execute(command)
        return result.toDto()
    }

    override fun deleteUser(req: UserDto.DeleteUserRequest) {
        val command = DeleteUserCommand.fromDto(req)
        deleteUserUseCase.execute(command)
    }
}
