package com.univent.api.iam.auth.organization.infrastructure

import com.univent.api.iam.auth.core.domain.PasswordHash
import com.univent.api.iam.auth.organization.domain.AuthOrganization
import com.univent.api.iam.auth.organization.domain.AuthOrganizationId
import com.univent.api.iam.auth.organization.domain.vo.AccountId
import com.univent.api.iam.organization.domain.OrganizationId
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import java.time.Instant

@Entity
@Table(
    name = "auth_organization",
    uniqueConstraints = [
        UniqueConstraint(name = "uk_auth_organization_account_id", columnNames = ["account_id"])
    ]
)
class AuthOrganizationEntity(
    @Id
    val id: Long,

    @Column(name = "account_id", nullable = false, unique = true)
    val accountId: String,

    @Column(nullable = false)
    val passwordHash: String,

    @Column(nullable = true)
    val refreshToken: String?,

    @Column(nullable = false)
    val organizationId: Long,

    @Column(nullable = false)
    val isDeleted: Boolean,

    @Column(nullable = true)
    val deletedAt: Instant?,

    @Column(nullable = false, updatable = false)
    val createdAt: Instant,

    @Column(nullable = false)
    val updatedAt: Instant
) {
    companion object {
        fun fromDomain(auth: AuthOrganization): AuthOrganizationEntity {
            return AuthOrganizationEntity(
                id = auth.id.value,
                accountId = auth.accountId.value,
                passwordHash = auth.passwordHash.value,
                refreshToken = auth.refreshToken,
                organizationId = auth.organizationId.value,
                isDeleted = auth.isDeleted,
                deletedAt = auth.deletedAt,
                createdAt = auth.createdAt,
                updatedAt = auth.updatedAt
            )
        }
    }

    /**
     * JPA Entity -> Domain Model 변환
     */
    fun toDomain(): AuthOrganization {
        return AuthOrganization.of(
            id = AuthOrganizationId(id),
            accountId = AccountId.create(accountId),
            passwordHash = PasswordHash(passwordHash),
            refreshToken = refreshToken,
            organizationId = OrganizationId(organizationId),
            isDeleted = isDeleted,
            deletedAt = deletedAt,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }
}
