package com.univent.api.common.core.infrastructure

import com.univent.api.common.core.domain.AggregateRoot
import com.univent.api.common.core.domain.DomainEventPublisher
import com.univent.api.common.core.domain.vo.Identifier
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component
import java.io.Serializable

@Component
internal class SpringDomainEventPublisher(
    private val eventPublisher: ApplicationEventPublisher
) : DomainEventPublisher {
    override fun <ID : Identifier<out Serializable>> publish(aggregateRoot: AggregateRoot<ID>) {
        val domainEvents = aggregateRoot.domainEvents

        if (domainEvents.isEmpty()) return

        try {
            domainEvents.forEach { eventPublisher.publishEvent(it) }
            aggregateRoot.clearDomainEvents()
        } catch (e: Exception) {
            throw e
        }
    }
}
