package com.univent.api.analytics.infrastructure

import com.amplitude.Amplitude
import com.univent.api.analytics.application.EventTracker
import mu.KotlinLogging
import org.springframework.stereotype.Component

@Component
class AmplitudeEventTracker(
    private val amplitude: Amplitude
) : EventTracker {
    private val logger = KotlinLogging.logger {}

    override fun track(userId: String, eventType: String, properties: Map<String, Any>?) {
        try {
            val event = com.amplitude.Event(eventType, userId).apply {
                properties?.forEach { (key, value) ->
                    this.eventProperties.put(key, value)
                }
            }

            amplitude.logEvent(event)

            logger.debug("앰플리튜드 이벤트 발생: $eventType for user: $userId")
        } catch (e: Exception) {
            logger.error("앰플리튜드 이벤트 오류: ${e.message}", e)
        }
    }
}
