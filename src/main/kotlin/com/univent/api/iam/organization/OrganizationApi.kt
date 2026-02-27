package com.univent.api.iam.organization

interface OrganizationApi {
    fun createOrganization(request: OrganizationDto.CreateOrganizationRequest): OrganizationDto.CreateOrganizationResponse
    fun deleteOrganization(request: OrganizationDto.DeleteOrganizationRequest)
}
