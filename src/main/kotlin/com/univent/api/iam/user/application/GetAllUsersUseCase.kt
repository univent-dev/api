package com.univent.api.iam.user.application

import com.univent.api.iam.user.application.query.GetAllUsersQuery
import com.univent.api.iam.user.application.view.UserAdminView

interface GetAllUsersUseCase {
    fun execute(query: GetAllUsersQuery): List<UserAdminView>
}
