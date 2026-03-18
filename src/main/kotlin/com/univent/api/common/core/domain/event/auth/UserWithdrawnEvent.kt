package com.univent.api.common.core.domain.event.auth

import com.univent.api.common.core.domain.DomainEvent

data class UserWithdrawnEvent(
    val userId: Long
): DomainEvent()
