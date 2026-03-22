package com.univent.api.iam.organization

sealed interface OrganizationDto {
    // Create Organization
    data class CreateOrganizationRequest(
        val name: String,
        val contact: String
    )

    data class CreateOrganizationResponse(
        val id: Long
    )

    // Delete Organization
    data class DeleteOrganizationRequest(
        val id: Long
    )
}
