package com.univent.api.iam.user.application.command

import com.univent.api.iam.user.UserDto
import com.univent.api.iam.user.domain.UserId

data class DeleteUserCommand(
    val id: UserId
) {
    companion object {
        fun fromDto(dto: UserDto.DeleteUserRequest): DeleteUserCommand = DeleteUserCommand(
                id = UserId(dto.id)
            )
    }
}
