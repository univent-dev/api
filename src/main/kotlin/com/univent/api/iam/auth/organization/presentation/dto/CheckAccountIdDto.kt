package com.univent.api.iam.auth.organization.presentation.dto

sealed interface CheckAccountIdDto {
    data class Req(
        val accountId: String
    )

    data class Res(
        val isDuplicated: Boolean
    )
}
