package com.univent.api.config.security

import com.univent.api.common.core.presentation.UserPayload
import com.univent.api.common.core.domain.vo.AccountRole
import com.univent.api.iam.JwtApi
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import java.security.SignatureException

class JwtAuthenticationFilter(
    private val jwtApi: JwtApi
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = extractToken(request)

        if (token != null) {
            try {
                val claims = jwtApi.getPayload(token, true)
                setAuthentication(claims.sub, claims.roles)
            } catch (e: Exception) {
                handleJwtException(request, e)
                SecurityContextHolder.clearContext()
            }
        }

        filterChain.doFilter(request, response)
    }

    private fun extractToken(request: HttpServletRequest): String? {
        val cookies = request.cookies ?: return null

        return cookies.find { it.name == "adminAccessToken" }?.value
            ?: cookies.find { it.name == "orgAccessToken" }?.value
            ?: cookies.find { it.name == "accessToken" }?.value
    }

    private fun setAuthentication(sub: Long, roles: List<AccountRole>) {
        val authorities = roles.map { SimpleGrantedAuthority(it.value) }

        val principal = UserPayload(
            userId = sub,
            roles = roles
        )

        val auth = UsernamePasswordAuthenticationToken(principal, null, authorities)
        SecurityContextHolder.getContext().authentication = auth
    }

    private fun handleJwtException(request: HttpServletRequest, e: Exception) {
        val message = when (e) {
            is SignatureException -> "Invalid JWT signature."
            is MalformedJwtException -> "Invalid JWT token."
            is ExpiredJwtException -> "JWT token is expired."
            is UnsupportedJwtException -> "JWT token is unsupported."
            is IllegalArgumentException -> "JWT claims string is empty."
            else -> "JWT authentication failed: ${e.message}"
        }

        request.setAttribute("jwtException", message)
        logger.error { "JWT Error: $message" }
    }
}
