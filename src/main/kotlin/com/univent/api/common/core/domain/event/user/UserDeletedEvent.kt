package com.univent.api.common.core.domain.event.user

import com.univent.api.common.core.domain.DomainEvent

class UserDeletedEvent(
    val userId: Long
): DomainEvent()