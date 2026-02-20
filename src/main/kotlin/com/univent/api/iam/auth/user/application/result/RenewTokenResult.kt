package com.univent.api.iam.auth.user.application.result

data class RenewTokenResult(
    val accessToken: String,
    val refreshToken: String
)
