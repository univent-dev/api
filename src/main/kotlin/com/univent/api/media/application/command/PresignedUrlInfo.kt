package com.univent.api.media.application.command

data class PresignedUrlInfo(
    val presignedUrl: String,
    val imageUrl: String
)
