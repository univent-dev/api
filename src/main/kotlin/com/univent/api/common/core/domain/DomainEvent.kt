package com.univent.api.common.core.domain

import com.univent.api.common.core.domain.vo.identifier.DomainEventId
import java.io.Serializable
import java.time.Instant
import java.util.UUID

abstract class DomainEvent(
    val eventId: DomainEventId = DomainEventId(UUID.randomUUID().toString()),
    val occurredOn: Instant = Instant.now()
) : Serializable {
    val eventType: String get() = this::class.simpleName ?: "UnknownEvent"

    override fun toString(): String {
        return "DomainEvent(type=$eventType, id=$eventId, occurredOn=$occurredOn)"
    }
}
