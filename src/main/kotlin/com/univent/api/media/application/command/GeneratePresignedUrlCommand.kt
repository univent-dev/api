package com.univent.api.media.application.command

import com.univent.api.common.core.domain.vo.identifier.ArticleId

data class GeneratePresignedUrlCommand(
    val articleId: ArticleId,
    val thumbnailInfo: FileInfo?,
    val fileInfoList: List<FileInfo>
)
