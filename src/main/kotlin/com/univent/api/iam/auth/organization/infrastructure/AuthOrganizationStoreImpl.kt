package com.univent.api.iam.auth.organization.infrastructure

import com.univent.api.iam.auth.organization.domain.AuthOrganization
import com.univent.api.iam.auth.organization.domain.AuthOrganizationStore
import com.univent.api.iam.auth.organization.domain.vo.AccountId
import com.univent.api.iam.organization.domain.OrganizationId
import org.springframework.stereotype.Repository

@Repository
class AuthOrganizationStoreImpl(
    private val authOrganizationJpaStore: AuthOrganizationJpaStore
) : AuthOrganizationStore {

    override fun save(authOrganization: AuthOrganization) {
        val entity = AuthOrganizationEntity.fromDomain(authOrganization)
        authOrganizationJpaStore.save(entity)
    }

    override fun update(authOrganization: AuthOrganization) {
        val entity = AuthOrganizationEntity.fromDomain(authOrganization)
        authOrganizationJpaStore.save(entity)
    }

    override fun findByOrganizationId(organizationId: OrganizationId): AuthOrganization? {
        return authOrganizationJpaStore.findByOrganizationId(organizationId.value)
            ?.toDomain()
    }

    override fun findByRefreshToken(refreshToken: String): AuthOrganization? {
        return authOrganizationJpaStore.findByRefreshToken(refreshToken)
            ?.toDomain()
    }

    override fun findByAccountId(accountId: AccountId): AuthOrganization? {
        return authOrganizationJpaStore.findByAccountId(accountId.value)
            ?.toDomain()
    }

    override fun existsByAccountId(accountId: AccountId): Boolean {
        return authOrganizationJpaStore.existsByAccountId(accountId.value)
    }

    override fun deleteByOrganizationId(organizationId: OrganizationId) {
        authOrganizationJpaStore.deleteByOrganizationId(organizationId.value)
    }
}
