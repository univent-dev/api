package com.univent.api.iam.auth.user.domain.event

import com.univent.api.common.core.domain.DomainEvent

data class LoginSucceededEvent(
    val userId: Long,
    val provider: String
): DomainEvent()
