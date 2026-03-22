package com.univent.api.analytics.application

import com.univent.api.common.core.domain.event.scrap.ScrapAddedEvent
import com.univent.api.common.core.domain.event.scrap.ScrapDeletedEvent
import org.springframework.modulith.events.ApplicationModuleListener
import org.springframework.stereotype.Component

@Component
class ScrapAnalyticsHandler(
    private val eventTracker: EventTracker
) {
    @ApplicationModuleListener
    fun onScrapAdded(event: ScrapAddedEvent) {
        eventTracker.track(
            userId = event.userId.toString(),
            eventType = "Article Scrapped",
            properties = mapOf("articleId" to event.articleId)
        )
    }

    @ApplicationModuleListener
    fun onScrapDeleted(event: ScrapDeletedEvent) {
        eventTracker.track(
            userId = event.userId.toString(),
            eventType = "Article Unscrapped",
            properties = mapOf("articleId" to event.articleId)
        )
    }
}
