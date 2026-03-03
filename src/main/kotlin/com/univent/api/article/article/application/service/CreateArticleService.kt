package com.univent.api.article.article.application.service

import com.univent.api.article.article.application.CreateArticleUseCase
import com.univent.api.article.article.application.command.CreateArticleCommand
import com.univent.api.article.article.application.result.CreateArticleResult
import com.univent.api.article.article.domain.Article
import com.univent.api.common.core.domain.vo.identifier.ArticleId
import com.univent.api.article.article.domain.ArticleStore
import com.univent.api.article.tag.TagApi
import com.univent.api.article.tag.TagDto
import com.univent.api.common.core.domain.IdGenerator
import com.univent.api.common.core.domain.vo.identifier.TagId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CreateArticleService(
    private val articleStore: ArticleStore,
    private val tagApi: TagApi,
    private val idGenerator: IdGenerator
) : CreateArticleUseCase {
    @Transactional
    override fun execute(command: CreateArticleCommand): CreateArticleResult {
        val tagIds = command.tagNames.let {
            tagApi.findOrCreateTags(
                TagDto.FindOrCreateTagsRequest(tagNames = it.toMutableSet())
            ).tagIds
        }

        val article = Article.create(
            id = ArticleId(idGenerator.generateId()),
            title = command.title,
            organizationId =command.organizationId,
            organization = command.organization,
            description = command.description,
            location = command.location,
            startAt = command.startAt,
            endAt = command.endAt,
            registrationUrl = command.registrationUrl,
            registrationStartAt = command.registrationStartAt,
            registrationEndAt = command.registrationEndAt,
            tagIds = tagIds.map { TagId(it) }
        )

        articleStore.save(article)

        return CreateArticleResult(articleId = article.id)
    }
}
