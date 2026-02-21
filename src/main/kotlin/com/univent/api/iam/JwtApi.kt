package com.univent.api.iam

import com.univent.api.common.core.domain.vo.AccountRole

interface JwtApi {
    fun createAccessToken(sub: Long, roles: List<AccountRole>): String
    fun createRefreshToken(sub: Long, roles: List<AccountRole>): String
    fun validateToken(token: String, isAccessToken: Boolean): Boolean
    fun getPayload(token: String, isAccessToken: Boolean): JwtDto.JwtPayload
}
