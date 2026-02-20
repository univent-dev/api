package com.univent.api.iam.auth.core.domain

import com.univent.api.iam.auth.user.domain.vo.OAuthProviderType

data class OAuthUser(
    val oauthId: String,
    val provider: OAuthProviderType,
    val email: String
)
