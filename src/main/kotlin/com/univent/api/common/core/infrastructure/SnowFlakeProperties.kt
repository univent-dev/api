package com.univent.api.common.core.infrastructure

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "snowflake")
data class SnowFlakeProperties (
    var datacenterId: Long = 0,
    var workerId: Long = 0
)
