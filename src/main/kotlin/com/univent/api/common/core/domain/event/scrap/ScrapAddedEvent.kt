package com.univent.api.common.core.domain.event.scrap

import com.univent.api.common.core.domain.DomainEvent

data class ScrapAddedEvent(
    val userId: Long,
    val articleId: Long,
    val tags: List<String>,
): DomainEvent()
