package com.univent.api.media.infrastructure.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("spring.cloud.aws.s3")
class AwsS3Properties(
    val cloudfrontDomain: String,
    val bucketName: String
)
