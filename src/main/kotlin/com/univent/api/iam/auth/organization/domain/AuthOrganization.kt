package com.univent.api.iam.auth.organization.domain

import com.univent.api.common.core.domain.AggregateRoot
import com.univent.api.iam.auth.core.domain.PasswordHash
import com.univent.api.iam.auth.organization.domain.vo.AccountId
import com.univent.api.iam.organization.domain.OrganizationId
import java.time.Instant

class AuthOrganization private constructor(
    id: AuthOrganizationId,
    accountId: AccountId,
    passwordHash: PasswordHash,
    refreshToken: String? = null,
    organizationId: OrganizationId,
    isDeleted: Boolean = false,
    deletedAt: Instant? = null,
    val createdAt: Instant = Instant.now(),
    updatedAt: Instant = Instant.now()
) : AggregateRoot<AuthOrganizationId>(id) {
    init { validate() }

    var accountId: AccountId = accountId
        private set

    var passwordHash: PasswordHash = passwordHash
        private set

    var refreshToken: String? = refreshToken
        private set

    var organizationId: OrganizationId = organizationId
        private set

    var isDeleted: Boolean = isDeleted
        private set

    var deletedAt: Instant? = deletedAt
        private set

    var updatedAt: Instant = updatedAt
        private set

    companion object {
        fun create(
            id: AuthOrganizationId,
            accountId: AccountId,
            passwordHash: PasswordHash,
            organizationId: OrganizationId
        ): AuthOrganization {
            return AuthOrganization(
                id = id,
                accountId = accountId,
                passwordHash = passwordHash,
                organizationId = organizationId
            )
        }

        fun of(
            id: AuthOrganizationId,
            accountId: AccountId,
            passwordHash: PasswordHash,
            refreshToken: String?,
            organizationId: OrganizationId,
            isDeleted: Boolean,
            deletedAt: Instant?,
            createdAt: Instant,
            updatedAt: Instant
        ): AuthOrganization = AuthOrganization(
            id = id,
            accountId = accountId,
            passwordHash = passwordHash,
            refreshToken = refreshToken,
            organizationId = organizationId,
            isDeleted = isDeleted,
            deletedAt = deletedAt,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }

    private fun validate() {

    }

    fun update(
        newAccountId: AccountId?,
        newPasswordHash: PasswordHash?,
        newRefreshToken: String?,
        newOrganizationId: OrganizationId?
    ) {
        newAccountId?.let { this.accountId = it }
        newPasswordHash?.let { this.passwordHash = it }
        newRefreshToken?.let { this.refreshToken = it }
        newOrganizationId?.let { this.organizationId = it }

        this.updatedAt = Instant.now()
        validate()
    }

    fun updateRefreshToken(token: String?) {
        this.refreshToken = token
        this.updatedAt = Instant.now()
    }

    fun updatePassword(newPasswordHash: PasswordHash) {
        this.passwordHash = newPasswordHash
        this.updatedAt = Instant.now()
    }

    fun delete() {
        if (!this.isDeleted) {
            this.isDeleted = true
            this.deletedAt = Instant.now()
            this.updatedAt = Instant.now()
        }
    }

    fun unDelete() {
        this.isDeleted = false
        this.deletedAt = null
        this.updatedAt = Instant.now()
    }
}