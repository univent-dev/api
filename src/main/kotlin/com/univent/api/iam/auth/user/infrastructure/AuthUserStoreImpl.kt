package com.univent.api.iam.auth.user.infrastructure

import com.univent.api.iam.auth.user.domain.AuthUserId
import com.univent.api.iam.auth.user.domain.AuthUser
import com.univent.api.iam.auth.user.domain.AuthUserStore
import com.univent.api.iam.user.domain.UserId
import org.springframework.stereotype.Repository

@Repository
class AuthUserStoreImpl(
    private val jpaStore: AuthUserJpaStore
): AuthUserStore {
    override fun save(authUser: AuthUser) {
        val entity = AuthUserEntity.fromDomain(authUser)
        jpaStore.save(entity)
    }

    override fun loadByOAuthIdAndProvider(
        oAuthId: String,
        provider: String
    ): AuthUser? {
        return jpaStore.findByOauthIdAndProvider(oAuthId, provider)?.toDomain()
    }

    override fun loadByRefreshToken(refreshToken: String): AuthUser? {
        return  jpaStore.findByRefreshToken(refreshToken)?.toDomain()
    }

    override fun loadByUserId(userId: UserId): AuthUser? {
        return jpaStore.findByUserId(userId.value)?.toDomain()
    }

    override fun deleteById(id: AuthUserId) {
        jpaStore.deleteById(id.value)
    }
}
