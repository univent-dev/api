package com.univent.api.iam.user.application.command

import com.univent.api.iam.user.UserDto

data class CreateUserCommand(
    val email: String
) {
    companion object {
        fun fromDto(dto: UserDto.CreateUserRequest): CreateUserCommand = CreateUserCommand(
                email = dto.email
            )
    }
}
