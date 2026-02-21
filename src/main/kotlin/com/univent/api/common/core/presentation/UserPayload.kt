package com.univent.api.common.core.presentation

import com.univent.api.common.core.domain.vo.AccountRole

data class UserPayload(
    val userId: Long,
    val roles: List<AccountRole>
)
