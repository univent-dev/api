package com.univent.api.iam

import com.univent.api.common.core.domain.vo.AccountRole

sealed class JwtDto {
    data class JwtPayload(
        val sub: Long,
        val roles: List<AccountRole>
    )
}
