package com.univent.api.iam.auth.user.application.command

import com.univent.api.iam.auth.core.domain.OAuthProviderType

data class OAuthLoginCommand(
    val oAuthProviderType: OAuthProviderType,
    val code: String,
    val state: String?
)
