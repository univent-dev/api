package com.univent.api.iam.user

sealed interface UserDto {
    // Create User
    data class CreateUserRequest(
        val email: String
    ) : UserDto

    data class CreateUserResponse(
        val id: Long
    ) : UserDto

    // Delete User
    data class DeleteUserRequest(
        val id: Long
    ) : UserDto
}
