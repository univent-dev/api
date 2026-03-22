package com.univent.api.iam.auth.organization.presentation.dto

sealed interface ChangeOrganizationPasswordDto {
    data class Req(
        val currentPassword: String,
        val newPassword: String
    )
}
