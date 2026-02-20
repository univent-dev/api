package com.univent.api.iam.auth.core.domain

data class OAuthUser(
    val oauthId: String,
    val provider: OAuthProviderType,
    val email: String
)
