package com.univent.api.article.scrap.infrastructure

import com.univent.api.article.scrap.application.view.ScrapReader
import org.springframework.stereotype.Repository

@Repository
class ScrapReaderImpl(
    private val scrapJpaReader: ScrapJpaReader
) : ScrapReader {
    override fun existsByArticleIdAndUserId(articleId: Long, userId: Long): Boolean {
        return scrapJpaReader.existsByArticleIdAndUserId(articleId, userId)
    }

    override fun findArticleIdsByUserId(userId: Long): List<Long> {
        return scrapJpaReader.findAllByUserId(userId).map { it.articleId }
    }
}
