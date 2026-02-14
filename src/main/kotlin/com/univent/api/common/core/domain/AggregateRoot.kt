package com.univent.api.common.core.domain

import com.univent.api.common.core.domain.vo.Identifier
import java.io.Serializable

abstract class AggregateRoot<ID : Identifier<out Serializable>>(id: ID): BaseDomainEntity<ID>(id) {
    private val _domainEvents = mutableListOf<DomainEvent>()
    val domainEvents: List<DomainEvent> get() = _domainEvents.toList()

    fun addDomainEvent(event: DomainEvent) {
        _domainEvents.add(event)
    }

    fun clearDomainEvents(): List<DomainEvent> {
        return _domainEvents.toList().also { _domainEvents.clear() }
    }
}
