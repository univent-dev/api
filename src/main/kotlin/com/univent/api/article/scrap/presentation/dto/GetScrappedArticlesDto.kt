package com.univent.api.article.scrap.presentation.dto

import com.univent.api.article.scrap.application.query.GetScrappedArticlesQuery

sealed interface GetScrappedArticlesDto {
    data class Req(
        val tags: List<String>?,
        val isFinished: Boolean?,
        val sortBy: String?,
        val page: Int = 0,
        val limit: Int = 10
    ) {
        fun toQuery(userId: Long) = GetScrappedArticlesQuery(
            userId = userId
        )
    }
}
