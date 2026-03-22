package com.univent.api.media.application.command

import com.univent.api.common.core.domain.vo.identifier.ArticleId

data class UpdateMediaCommand(
    val articleId: ArticleId,
    val thumbnailInfo: MediaInfo?,
    val fileInfoList: List<MediaInfo>
)
