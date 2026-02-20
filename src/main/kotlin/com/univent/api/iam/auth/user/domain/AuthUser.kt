package com.univent.api.iam.auth.user.domain

import com.univent.api.common.core.domain.AggregateRoot
import com.univent.api.common.exception.CustomException
import com.univent.api.iam.auth.core.domain.AuthErrorCode
import com.univent.api.iam.auth.user.domain.event.LoginSucceededEvent
import com.univent.api.iam.auth.user.domain.event.UserSignedUpEvent
import com.univent.api.iam.auth.user.domain.event.UserWithdrawnEvent
import com.univent.api.iam.auth.core.domain.OAuthProviderType
import com.univent.api.iam.user.domain.UserId
import java.time.Instant

class AuthUser private constructor(
    id: AuthUserId,
    val userId: UserId,
    val oAuthId: String,
    val provider: OAuthProviderType,
    refreshToken: String? = null,
    oAuthAccessToken: String? = null,
    val createdAt: Instant = Instant.now(),
    updatedAt: Instant = Instant.now()
) : AggregateRoot<AuthUserId>(id) {

    var refreshToken: String? = refreshToken
        private set

    var oAuthAccessToken: String? = oAuthAccessToken
        private set

    var updatedAt: Instant = updatedAt
        private set

    init {
        validate()
    }

    companion object {
        fun create(
            id: AuthUserId,
            userId: UserId,
            oAuthId: String,
            provider: OAuthProviderType
        ): AuthUser {
            return AuthUser(id, userId, oAuthId, provider).apply {
                addDomainEvent(UserSignedUpEvent(userId.value, oAuthId, provider.name))
                addDomainEvent(LoginSucceededEvent(userId.value, provider.name))
            }
        }

        fun of(
            id: AuthUserId,
            userId: UserId,
            oAuthId: String,
            provider: OAuthProviderType,
            refreshToken: String?,
            oAuthAccessToken: String?,
            createdAt: Instant,
            updatedAt: Instant
        ): AuthUser = AuthUser(
            id, userId, oAuthId, provider,
            refreshToken, oAuthAccessToken, createdAt, updatedAt
        )
    }

    fun validate() {
        require(oAuthId.isNotBlank()) { CustomException(AuthErrorCode.AUTH_OAUTH_ID_EMPTY) }
    }

    fun updateRefreshToken(newRefreshToken: String?) {
        this.refreshToken = newRefreshToken
        this.updatedAt = Instant.now()
    }

    fun delete() {
        addDomainEvent(UserWithdrawnEvent(id.value))
    }
}
