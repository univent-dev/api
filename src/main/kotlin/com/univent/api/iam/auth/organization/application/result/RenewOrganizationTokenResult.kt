package com.univent.api.iam.auth.organization.application.result

data class RenewOrganizationTokenResult(
    val accessToken: String,
    val refreshToken: String
)
