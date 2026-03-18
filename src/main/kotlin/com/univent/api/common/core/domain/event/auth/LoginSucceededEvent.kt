package com.univent.api.common.core.domain.event.auth

import com.univent.api.common.core.domain.DomainEvent

data class LoginSucceededEvent(
    val userId: Long,
    val provider: String
): DomainEvent()
