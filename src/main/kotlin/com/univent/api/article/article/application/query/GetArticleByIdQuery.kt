package com.univent.api.article.article.application.query

import com.univent.api.article.article.ArticleDto

data class GetArticleByIdQuery(
    val id: Long
) {
    companion object {
        fun fromDto(dto: ArticleDto.GetArticleByIdRequest): GetArticleByIdQuery {
            return GetArticleByIdQuery(
                id = dto.articleId
            )
        }
    }
}
