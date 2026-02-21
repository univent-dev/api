package com.univent.api.iam.auth.user.infrastructure

import org.springframework.data.jpa.repository.JpaRepository

interface AuthUserJpaStore: JpaRepository<AuthUserEntity, Long> {
    fun findByOauthIdAndProvider(oauthId: String, provider: String): AuthUserEntity?
    fun findByRefreshToken(refreshToken: String?): AuthUserEntity?
    fun findByUserId(userId: Long): AuthUserEntity?
}
