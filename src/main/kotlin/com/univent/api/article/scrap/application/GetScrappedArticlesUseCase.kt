package com.univent.api.article.scrap.application

import com.univent.api.article.article.ArticleDto
import com.univent.api.article.scrap.application.query.GetScrappedArticlesQuery

interface GetScrappedArticlesUseCase {
    fun execute(query: GetScrappedArticlesQuery): List<ArticleDto.ArticleSummary>
}
