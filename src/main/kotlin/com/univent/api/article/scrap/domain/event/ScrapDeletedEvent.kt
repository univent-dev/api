package com.univent.api.article.scrap.domain.event

import com.univent.api.common.core.domain.DomainEvent

data class ScrapDeletedEvent(
    val userId: Long,
    val articleId: Long,
    val tags: List<String>,
): DomainEvent()
