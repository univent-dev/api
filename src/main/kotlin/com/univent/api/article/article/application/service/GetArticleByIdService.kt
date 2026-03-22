package com.univent.api.article.article.application.service

import com.univent.api.article.article.application.GetArticleByIdUseCase
import com.univent.api.article.article.application.query.GetArticleByIdQuery
import com.univent.api.article.article.application.view.ArticleDetailView
import com.univent.api.article.article.application.view.ArticleReader
import com.univent.api.article.article.domain.ArticleErrorCode
import com.univent.api.article.tag.application.view.TagReader
import com.univent.api.common.exception.CustomException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GetArticleByIdService(
    private val articleReader: ArticleReader,
    private val tagReader: TagReader
) : GetArticleByIdUseCase {
    @Transactional
    override fun execute(query: GetArticleByIdQuery): ArticleDetailView {
        val articleView = articleReader.findById(query.id)
            ?: throw CustomException(ArticleErrorCode.ARTICLE_NOT_FOUND)

        val tags = tagReader.findAllByIds(articleView.tagIds)
        val tagNames = tags.map { it.name }

        val result = ArticleDetailView(
            id = articleView.id,
            title = articleView.title,
            organization = articleView.organization,
            scrapCount = articleView.scrapCount,
            viewCount = articleView.viewCount,
            startAt = articleView.startAt,
            endAt = articleView.endAt,
            registrationStartAt = articleView.registrationStartAt,
            registrationEndAt = articleView.registrationEndAt,
            description = articleView.description,
            location = articleView.location,
            registrationUrl = articleView.registrationUrl,
            tags = tagNames,
            thumbnailPath = "",
            imagePaths = listOf()
        )

        return result
    }
}
