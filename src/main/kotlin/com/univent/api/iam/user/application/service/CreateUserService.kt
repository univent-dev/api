package com.univent.api.iam.user.application.service

import com.univent.api.common.core.domain.IdGenerator
import com.univent.api.iam.user.application.CreateUserUseCase
import com.univent.api.iam.user.application.command.CreateUserCommand
import com.univent.api.iam.user.application.result.CreateUserResult
import com.univent.api.iam.user.domain.User
import com.univent.api.iam.user.domain.UserId
import com.univent.api.iam.user.domain.UserStore
import org.springframework.stereotype.Service

@Service
class CreateUserService(
    private val userStore: UserStore,
    private val idGenerator: IdGenerator
): CreateUserUseCase {
    override fun execute(command: CreateUserCommand): CreateUserResult {
        val user = User.create(
            id = UserId(idGenerator.generateId()),
            email = command.email
        )
        userStore.save(user)

        return CreateUserResult(user.id)
    }
}
