package com.univent.api.article.article.application.command

import com.univent.api.article.article.ArticleDto
import com.univent.api.common.core.domain.vo.identifier.ArticleId

data class IncreaseScrapCountCommand(
    val id: ArticleId
) {
    companion object {
        fun fromDto(dto: ArticleDto.IncreaseScrapCountRequest): IncreaseScrapCountCommand {
            return IncreaseScrapCountCommand(
                id = ArticleId(dto.articleId)
            )
        }
    }
}
