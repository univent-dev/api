package com.univent.api.media.infrastructure.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("spring.cloud.aws")
class AwsS3Properties(
    val cloudfrontDomain: String
)
