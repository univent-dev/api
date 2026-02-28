package com.univent.api.iam.organization.infrastructure

import com.univent.api.iam.organization.domain.Organization
import com.univent.api.iam.organization.domain.OrganizationId
import com.univent.api.iam.organization.domain.OrganizationStore
import org.springframework.stereotype.Repository

@Repository
class OrganizationStoreImpl(
    private val organizationJpaStore: OrganizationJpaStore
) : OrganizationStore {
    override fun save(organization: Organization) {
        val entity = OrganizationEntity.fromDomain(organization)
        organizationJpaStore.save(entity)
    }

    override fun loadById(id: OrganizationId): Organization? {
        return organizationJpaStore.findById(id.value)
            .map { it.toDomain() }
            .orElse(null)
    }

    override fun update(organization: Organization) {
        val entity = OrganizationEntity.fromDomain(organization)
        organizationJpaStore.save(entity)
    }

    override fun deleteById(id: OrganizationId) {
        organizationJpaStore.deleteById(id.value)
    }
}
