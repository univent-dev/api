package com.univent.api.iam.organization.application.command

import com.univent.api.iam.organization.OrganizationDto

data class CreateOrganizationCommand(
    val name: String,
    val contact: String
) {
    companion object {
        fun fromDto(dto: OrganizationDto.CreateOrganizationRequest) = CreateOrganizationCommand(
            name = dto.name,
            contact = dto.contact
        )
    }
}
