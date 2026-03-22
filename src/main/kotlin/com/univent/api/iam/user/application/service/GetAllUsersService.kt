package com.univent.api.iam.user.application.service

import com.univent.api.iam.user.application.GetAllUsersUseCase
import com.univent.api.iam.user.application.query.GetAllUsersQuery
import com.univent.api.iam.user.application.view.UserAdminView
import com.univent.api.iam.user.application.view.UserReader
import org.springframework.stereotype.Service

@Service
class GetAllUsersService(
    private val userReader: UserReader
): GetAllUsersUseCase {
    override fun execute(query: GetAllUsersQuery): List<UserAdminView> {
        return userReader.findAllByCursor(
            pageSize = query.pageSize,
            cursorId = query.cursorId,
            cursorDate = query.cursorDate
        )
    }
}
