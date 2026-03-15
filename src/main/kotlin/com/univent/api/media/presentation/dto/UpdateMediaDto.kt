package com.univent.api.media.presentation.dto

import com.univent.api.common.core.domain.vo.identifier.ArticleId
import com.univent.api.media.application.command.UpdateMediaCommand

sealed interface UpdateMediaDto {
    data class Req(
        val articleId: Long,
        val thumbnailInfo: MediaInfoDto?,
        val fileInfoList: List<MediaInfoDto>
    ) {
        fun toCommand() = UpdateMediaCommand(
            articleId = ArticleId(articleId),
            thumbnailInfo = thumbnailInfo?.toCommand(),
            fileInfoList = fileInfoList.map { it.toCommand() }
        )
    }
}
