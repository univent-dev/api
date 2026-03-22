package com.univent.api.common.core.presentation

import com.univent.api.common.core.domain.vo.AccountRole

data class UserPayload(
    override val id: Long,
    override val roles: List<AccountRole>
): AuthenticationPayload
