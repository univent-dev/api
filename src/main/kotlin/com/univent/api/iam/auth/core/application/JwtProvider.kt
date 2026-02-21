package com.univent.api.iam.auth.core.application

import com.univent.api.common.core.domain.vo.AccountRole
import io.jsonwebtoken.Claims

interface JwtProvider {
    fun generateToken(sub: String, roles: List<AccountRole>, isAccessToken: Boolean): String
    fun getSubject(token: String, isAccessToken: Boolean): String
    fun getClaims(token: String, isAccessToken: Boolean): Claims
    fun validateToken(token: String, isAccessToken: Boolean): Boolean
}