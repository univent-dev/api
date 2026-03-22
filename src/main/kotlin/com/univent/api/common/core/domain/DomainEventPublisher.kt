package com.univent.api.common.core.domain

import com.univent.api.common.core.domain.vo.Identifier
import java.io.Serializable

interface DomainEventPublisher {
    fun <ID : Identifier<out Serializable>> publish(aggregateRoot: AggregateRoot<ID>)
}
