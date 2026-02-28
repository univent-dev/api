package com.univent.api.iam.auth.organization.infrastructure

import org.springframework.data.jpa.repository.JpaRepository

interface AuthOrganizationJpaStore: JpaRepository<AuthOrganizationEntity, Long> {
    fun findByOrganizationId(organizationId: Long): AuthOrganizationEntity?
    fun findByRefreshToken(refreshToken: String): AuthOrganizationEntity?
    fun findByAccountId(accountId: String): AuthOrganizationEntity?
    fun existsByAccountId(accountId: String): Boolean
    fun deleteByOrganizationId(organizationId: Long)
}
