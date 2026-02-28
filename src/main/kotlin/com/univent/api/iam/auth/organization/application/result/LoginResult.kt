package com.univent.api.iam.auth.organization.application.result

data class LoginResult(
    val accessToken: String,
    val refreshToken: String
)
