package com.univent.api.iam.auth.user.application.command

import com.univent.api.iam.user.domain.UserId

data class LogoutCommand(
    val userId: UserId
)
