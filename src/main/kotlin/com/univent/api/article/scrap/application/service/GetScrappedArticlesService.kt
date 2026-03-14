package com.univent.api.article.scrap.application.service

import com.univent.api.article.article.ArticleApi
import com.univent.api.article.article.ArticleDto
import com.univent.api.article.scrap.application.GetScrappedArticlesUseCase
import com.univent.api.article.scrap.application.query.GetScrappedArticlesQuery
import com.univent.api.article.scrap.application.view.ScrapReader
import org.springframework.stereotype.Service

@Service
class GetScrappedArticlesService(
    private val scrapReader: ScrapReader,
    private val articleApi: ArticleApi
): GetScrappedArticlesUseCase {
    override fun execute(query: GetScrappedArticlesQuery): List<ArticleDto.ArticleSummary> {
        val articleIds = scrapReader.findArticleIdsByUserId(query.userId)
        if (articleIds.isEmpty()) {
            return emptyList()
        }

        val articles = articleApi.getArticlesByIds(ArticleDto.GetArticlesByIdsRequest(articleIds))

        return articles
    }
}
