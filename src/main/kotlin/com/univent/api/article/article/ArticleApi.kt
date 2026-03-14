package com.univent.api.article.article

interface ArticleApi {
    fun increaseScrapCount(request: ArticleDto.IncreaseScrapCountRequest)
    fun decreaseScrapCount(request: ArticleDto.DecreaseScrapCountRequest)
    fun getArticleById(request: ArticleDto.GetArticleByIdRequest): ArticleDto.GetArticleByIdResponse
    fun getArticlesByIds(request: ArticleDto.GetArticlesByIdsRequest): List<ArticleDto.ArticleSummary>
}
