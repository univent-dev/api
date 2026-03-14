package com.univent.api.article.scrap.application.service

import com.univent.api.article.scrap.application.GetScrapStatusUseCase
import com.univent.api.article.scrap.application.query.GetScrapStatusQuery
import com.univent.api.article.scrap.application.view.ScrapReader
import org.springframework.stereotype.Service

@Service
class GetScrapStatusService(
    private val scrapReader: ScrapReader
): GetScrapStatusUseCase {
    override fun execute(query: GetScrapStatusQuery): Boolean {
        return scrapReader.existsByArticleIdAndUserId(query.articleId, query.userId)
    }
}
