package com.univent.api.iam.user.application

import com.univent.api.iam.user.application.query.GetUserQuery
import com.univent.api.iam.user.application.view.UserView

interface GetUserUseCase {
    fun execute(query: GetUserQuery): UserView
}
