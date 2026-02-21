package com.univent.api.iam.auth.core.infrastructure.jwt

import com.univent.api.common.core.domain.vo.AccountRole
import com.univent.api.iam.auth.core.application.JwtProvider
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.util.Date
import javax.crypto.SecretKey

@Component
class JwtProviderImpl(
    private val properties: JwtProperties
): JwtProvider {
    private fun getSecretKey(secret: String): SecretKey =
        Keys.hmacShaKeyFor(secret.toByteArray())

    override fun generateToken(sub: String, roles: List<AccountRole>, isAccessToken: Boolean): String {
        val info = if (isAccessToken) properties.access else properties.refresh
        val now = Date()
        val expiryDate = Date(now.time + info.expiration * 1000)

        return Jwts.builder()
            .claim("roles", roles.map { it.name })
            .subject(sub)
            .issuedAt(now)
            .expiration(expiryDate)
            .signWith(getSecretKey(info.secret))
            .compact()
    }

    override fun getSubject(token: String, isAccessToken: Boolean): String {
        return getClaims(token, isAccessToken).subject
    }

    override fun getClaims(token: String, isAccessToken: Boolean): Claims {
        val secret = if (isAccessToken) properties.access.secret else properties.refresh.secret

        return Jwts.parser()
            .verifyWith(getSecretKey(secret))
            .build()
            .parseSignedClaims(token)
            .payload
    }

    override fun validateToken(token: String, isAccessToken: Boolean): Boolean {
        return try {
            getClaims(token, isAccessToken)
            true
        } catch (e: Exception) {
            false
        }
    }
}
