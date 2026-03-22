package com.univent.api.article.article.application.service

import com.univent.api.article.article.application.GetArticlesByIdsUseCase
import com.univent.api.article.article.application.query.GetArticlesByIdsQuery
import com.univent.api.article.article.application.view.ArticleReader
import com.univent.api.article.article.application.view.ArticleView
import com.univent.api.article.tag.application.view.TagReader
import org.springframework.stereotype.Service

@Service
class GetArticlesByIdsService(
    private val articleReader: ArticleReader,
    private val tagReader: TagReader
): GetArticlesByIdsUseCase {
    override fun execute(query: GetArticlesByIdsQuery): List<ArticleView> {
        if (query.ids.isEmpty()) return emptyList()

        val readModels = articleReader.findAllByIds(query.ids)
        val allTagIds = readModels.flatMap { it.tagIds }.toSet()


        val tagMap = if (allTagIds.isNotEmpty()) {
            tagReader.findAllByIds(allTagIds).associate { it.id to it.name }
        } else {
            emptyMap()
        }

        return readModels.map { model ->
            ArticleView(
                id = model.id,
                title = model.title,
                organization = model.organization,
                tags = model.tagIds.mapNotNull { tagMap[it] },
                scrapCount = model.scrapCount,
                viewCount = model.viewCount,
                startAt = model.startAt,
                endAt = model.endAt,
                thumbnailPath = "",
                registrationEndAt = model.registrationEndAt,
                registrationStartAt = model.registrationStartAt
            )
        }
    }
}
