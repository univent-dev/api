package com.univent.api.iam.auth.user.domain.event

import com.univent.api.common.core.domain.DomainEvent

data class UserSignedUpEvent(
    val userId: Long,
    val email: String,
    val provider: String
): DomainEvent()
