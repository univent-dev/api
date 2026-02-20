package com.univent.api.iam.auth.user.infrastructure

import com.univent.api.iam.auth.user.domain.AuthUserId
import com.univent.api.iam.auth.user.domain.AuthUser
import com.univent.api.iam.auth.core.domain.OAuthProviderType
import com.univent.api.iam.user.domain.UserId
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import java.time.Instant

@Entity
@Table(
    name = "auth",
    uniqueConstraints = [
        UniqueConstraint(
            columnNames = ["oauth_id", "provider"]
        )
    ]
)
class AuthUserEntity(
    @Id
    val id: Long,

    @Column(nullable = false)
    val oauthId: String,

    @Column(nullable = false)
    val provider: String,

    @Column(unique = true)
    val refreshToken: String?,

    @Column(unique = true)
    val oAuthAccessToken: String?,

    @Column(nullable = false, unique = true)
    val userId: Long,

    @Column(nullable = false)
    val createdAt: Instant = Instant.now(),

    @Column(nullable = false)
    val updatedAt: Instant = Instant.now()
) {
    companion object {
        fun fromDomain(authUser: AuthUser): AuthUserEntity {
            return AuthUserEntity(
                id = authUser.id.value,
                userId = authUser.userId.value,
                oauthId = authUser.oAuthId,
                provider = authUser.provider.value,
                refreshToken = authUser.refreshToken,
                oAuthAccessToken = authUser.oAuthAccessToken,
                createdAt = authUser.createdAt,
                updatedAt = authUser.updatedAt
            )
        }
    }

    fun toDomain(): AuthUser {
        return AuthUser.of(
            id = AuthUserId(id),
            userId = UserId(userId),
            oAuthId = oauthId,
            provider = OAuthProviderType.from(provider),
            refreshToken = refreshToken,
            oAuthAccessToken = oAuthAccessToken,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }
}
