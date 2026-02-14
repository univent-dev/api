package com.univent.api.iam.user.domain.event

import com.univent.api.common.core.domain.DomainEvent

class UserDeletedEvent(
    val userId: Long
): DomainEvent()
