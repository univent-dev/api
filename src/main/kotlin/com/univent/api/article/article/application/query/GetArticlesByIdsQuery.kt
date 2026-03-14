package com.univent.api.article.article.application.query

import com.univent.api.article.article.ArticleDto

data class GetArticlesByIdsQuery(
    val ids: List<Long>
) {
    companion object {
        fun fromDto(dto: ArticleDto.GetArticlesByIdsRequest): GetArticlesByIdsQuery {
            return GetArticlesByIdsQuery(
                ids = dto.articleIds
            )
        }
    }
}
