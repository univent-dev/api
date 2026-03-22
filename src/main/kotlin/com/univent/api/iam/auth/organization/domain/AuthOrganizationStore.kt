package com.univent.api.iam.auth.organization.domain

import com.univent.api.iam.auth.organization.domain.vo.AccountId
import com.univent.api.iam.organization.domain.OrganizationId

interface AuthOrganizationStore {
    fun save(authOrganization: AuthOrganization)
    fun update(authOrganization: AuthOrganization)
    fun findByOrganizationId(organizationId: OrganizationId): AuthOrganization?
    fun findByRefreshToken(refreshToken: String): AuthOrganization?
    fun findByAccountId(accountId: AccountId): AuthOrganization?
    fun existsByAccountId(accountId: AccountId): Boolean
    fun deleteByOrganizationId(organizationId: OrganizationId)
}
