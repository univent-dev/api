package com.univent.api.iam.auth.core.application

import com.univent.api.common.core.domain.vo.AccountRole
import com.univent.api.iam.JwtApi
import com.univent.api.iam.JwtDto
import org.springframework.stereotype.Service

@Service
class JwtApiImpl(
    private val jwtProvider: JwtProvider
): JwtApi {
    override fun createAccessToken(
        sub: Long,
        roles: List<AccountRole>
    ): String {
        return jwtProvider.generateToken(
            sub = sub.toString(),
            roles = roles,
            isAccessToken = true
        )
    }

    override fun createRefreshToken(sub: Long, roles: List<AccountRole>): String {
        return jwtProvider.generateToken(
            sub = sub.toString(),
            roles = roles,
            isAccessToken = false
        )
    }

    override fun validateToken(token: String, isAccessToken: Boolean): Boolean {
        return jwtProvider.validateToken(token, isAccessToken)
    }

    override fun getPayload(
        token: String,
        isAccessToken: Boolean
    ): JwtDto.JwtPayload {
        val claims = jwtProvider.getClaims(token, isAccessToken)
        val roles = claims["roles"] as? List<AccountRole> ?: emptyList()

        return JwtDto.JwtPayload(
            sub = claims.subject.toLong(),
            roles = roles
        )
    }
}
