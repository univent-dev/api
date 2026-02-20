package com.univent.api.common.core.presentation

import com.univent.api.config.security.AccountRole

data class UserPayload(
    val userId: Long,
    val roles: List<AccountRole>
)
