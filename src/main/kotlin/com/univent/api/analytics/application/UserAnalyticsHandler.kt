package com.univent.api.analytics.application

import com.univent.api.common.core.domain.event.auth.LoginSucceededEvent
import com.univent.api.common.core.domain.event.auth.UserSignedUpEvent
import org.springframework.modulith.events.ApplicationModuleListener
import org.springframework.stereotype.Component

@Component
class UserAnalyticsHandler(
    private val eventTracker: EventTracker
) {
    @ApplicationModuleListener
    fun onSignedUp(event: UserSignedUpEvent) {
        eventTracker.track(event.userId.toString(), "Signed Up", mapOf("email" to event.email))
    }

    @ApplicationModuleListener
    fun onLoginSucceeded(event: LoginSucceededEvent) {
        eventTracker.track(event.userId.toString(), "Logged In")
    }
}
