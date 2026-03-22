package com.univent.api.iam.organization.domain

interface OrganizationStore {
    fun save(organization: Organization)
    fun loadById(id: OrganizationId): Organization?
    fun update(organization: Organization)
    fun deleteById(id: OrganizationId)
}
