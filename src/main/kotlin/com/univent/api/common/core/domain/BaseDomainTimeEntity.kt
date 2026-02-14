package com.univent.api.common.core.domain

import java.time.Instant

abstract class BaseDomainTimeEntity {
    val createdAt: Instant = Instant.now()
    var updatedAt: Instant = Instant.now()
        private set

    protected fun updateTime() {
        this.updatedAt = Instant.now()
    }
}
