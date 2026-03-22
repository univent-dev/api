package com.univent.api.iam.auth.user.application.result

import com.univent.api.iam.user.domain.UserId

data class OAuthLoginResult(
    val accessToken: String,
    val refreshToken: String,
    val userId: UserId,
    val redirectUrl: String
)
