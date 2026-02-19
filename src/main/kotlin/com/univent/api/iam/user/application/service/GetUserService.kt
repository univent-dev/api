package com.univent.api.iam.user.application.service

import com.univent.api.common.exception.CustomException
import com.univent.api.iam.user.application.GetUserUseCase
import com.univent.api.iam.user.application.query.GetUserQuery
import com.univent.api.iam.user.application.view.UserReader
import com.univent.api.iam.user.application.view.UserView
import com.univent.api.iam.user.domain.UserErrorCode
import org.springframework.stereotype.Service

@Service
class GetUserService(
    private val userReader: UserReader
): GetUserUseCase {
    override fun execute(query: GetUserQuery): UserView {
        return userReader.findById(query.id) ?: throw CustomException(UserErrorCode.USER_NOT_FOUND)
    }
}
