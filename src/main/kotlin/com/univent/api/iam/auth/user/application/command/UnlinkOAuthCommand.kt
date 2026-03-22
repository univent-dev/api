package com.univent.api.iam.auth.user.application.command

import com.univent.api.iam.auth.core.domain.OAuthProviderType
import com.univent.api.iam.user.domain.UserId

data class UnlinkOAuthCommand(
    val userId: UserId,
    val provider: OAuthProviderType
)
