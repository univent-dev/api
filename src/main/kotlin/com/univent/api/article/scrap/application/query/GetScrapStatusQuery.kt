package com.univent.api.article.scrap.application.query

data class GetScrapStatusQuery(
    val articleId: Long,
    val userId: Long
)
