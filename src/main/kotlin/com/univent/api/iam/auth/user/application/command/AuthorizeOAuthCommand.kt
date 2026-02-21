package com.univent.api.iam.auth.user.application.command

import com.univent.api.iam.auth.core.domain.OAuthProviderType

data class AuthorizeOAuthCommand(
    val oAuthProviderType: OAuthProviderType,
    val redirectUrl: String?
)
