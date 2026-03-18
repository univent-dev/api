package com.univent.api.analytics.application

interface EventTracker {
    fun track(userId: String, eventType: String, properties: Map<String, Any>? = null)
}
