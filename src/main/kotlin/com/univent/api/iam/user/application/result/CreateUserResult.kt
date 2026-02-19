package com.univent.api.iam.user.application.result

import com.univent.api.iam.user.UserDto.CreateUserResponse
import com.univent.api.iam.user.domain.UserId

data class CreateUserResult(
    val id: UserId
) {
    fun toDto(): CreateUserResponse = CreateUserResponse(
            id = id.value
        )
}
