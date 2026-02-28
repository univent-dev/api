package com.univent.api.iam.auth.organization.application.command

data class LoginCommand(
    val accountId: String,
    val password: String
)
