package com.univent.api.iam.auth.organization.presentation.dto

sealed interface RegisterOrganizationDto {
    data class Req(
        val accountId: String,
        val password: String,
        val name: String,
        val contact: String
    )
}
