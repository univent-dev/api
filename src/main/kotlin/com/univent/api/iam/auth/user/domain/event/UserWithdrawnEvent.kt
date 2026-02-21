package com.univent.api.iam.auth.user.domain.event

import com.univent.api.common.core.domain.DomainEvent

data class UserWithdrawnEvent(
    val userId: Long
): DomainEvent()
