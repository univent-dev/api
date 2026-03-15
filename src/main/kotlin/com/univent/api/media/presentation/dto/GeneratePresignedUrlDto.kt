package com.univent.api.media.presentation.dto

import com.univent.api.common.core.domain.vo.identifier.ArticleId
import com.univent.api.media.application.command.GeneratePresignedUrlCommand
import com.univent.api.media.application.command.PresignedUrlInfo
import com.univent.api.media.application.result.GeneratePresignedUrlResult

sealed interface GeneratePresignedUrlDto {
    data class Req(
        val articleId: Long,
        val thumbnailInfo: FileInfoDto?,
        val fileInfoList: List<FileInfoDto>
    ) {
        fun toCommand() = GeneratePresignedUrlCommand(
            articleId = ArticleId(articleId),
            thumbnailInfo = thumbnailInfo?.toCommand(),
            fileInfoList = fileInfoList.map { it.toCommand() }
        )
    }

    data class Res(
        val thumbnailPresignedUrl: PresignedUrlInfo?,
        val presignedUrls: List<PresignedUrlInfo>
    ) {
        companion object {
            fun fromResult(result: GeneratePresignedUrlResult) = Res(
                thumbnailPresignedUrl = result.thumbnailPresignedUrl,
                presignedUrls = result.presignedUrls
            )
        }
    }
}
