package com.univent.api.common.core.presentation

import com.univent.api.common.core.domain.vo.AccountRole

interface AuthenticationPayload {
    val id: Long
    val roles: List<AccountRole>
}
