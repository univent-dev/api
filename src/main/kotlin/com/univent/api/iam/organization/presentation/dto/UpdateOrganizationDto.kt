package com.univent.api.iam.organization.presentation.dto

sealed class UpdateOrganizationDto {
    data class Req(
        val name: String,
        val contact: String?
    ) : UpdateOrganizationDto()
}
