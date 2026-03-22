package com.univent.api.iam.auth.user.domain

import com.univent.api.iam.user.domain.UserId

interface AuthUserStore {
    fun save(authUser: AuthUser)
    fun loadByOAuthIdAndProvider(oAuthId: String, provider: String): AuthUser?
    fun loadByRefreshToken(refreshToken: String): AuthUser?
    fun loadByUserId(userId: UserId): AuthUser?
    fun deleteById(id: AuthUserId)
}
