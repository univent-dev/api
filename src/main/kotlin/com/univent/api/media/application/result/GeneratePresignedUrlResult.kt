package com.univent.api.media.application.result

import com.univent.api.media.application.command.PresignedUrlInfo

data class GeneratePresignedUrlResult(
    val thumbnailPresignedUrl: PresignedUrlInfo?,
    val presignedUrls: List<PresignedUrlInfo>
)
