package com.univent.api.iam.auth.organization.presentation.dto

sealed interface OrganizationLoginDto {
    data class Req(
        val accountId: String,
        val password: String
    )
}
