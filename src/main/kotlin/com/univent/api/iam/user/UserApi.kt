package com.univent.api.iam.user

interface UserApi {
    fun createUser(request: UserDto.CreateUserRequest): UserDto.CreateUserResponse
    fun deleteUser(request: UserDto.DeleteUserRequest)
}
