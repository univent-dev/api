package com.univent.api.article.scrap.application.service

import com.univent.api.article.article.ArticleApi
import com.univent.api.article.article.ArticleDto
import com.univent.api.article.scrap.application.AddScrapUseCase
import com.univent.api.article.scrap.application.command.AddScrapCommand
import com.univent.api.article.scrap.domain.Scrap
import com.univent.api.article.scrap.domain.ScrapErrorCode
import com.univent.api.article.scrap.domain.ScrapStore
import com.univent.api.article.scrap.domain.event.ScrapAddedEvent
import com.univent.api.common.core.domain.DomainEventPublisher
import com.univent.api.common.core.domain.IdGenerator
import com.univent.api.common.core.domain.vo.identifier.ScrapId
import com.univent.api.common.exception.CustomException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AddScrapService(
    private val scrapStore: ScrapStore,
    private val articleApi: ArticleApi,
    private val idGenerator: IdGenerator,
    private val domainEventPublisher: DomainEventPublisher
) : AddScrapUseCase {
    @Transactional
    override fun execute(command: AddScrapCommand) {
        val existingScrap = scrapStore.loadByArticleIdAndUserId(command.articleId, command.userId)
        if (existingScrap != null) throw CustomException(ScrapErrorCode.SCRAP_ALREADY_EXISTS)

        val article = articleApi.getArticleById(ArticleDto.GetArticleByIdRequest(command.articleId.value))

        val scrap = Scrap.create(
            id = ScrapId(idGenerator.generateId()),
            articleId = command.articleId,
            userId = command.userId,
            tags = article.tags
        )

        scrapStore.save(scrap)

        articleApi.increaseScrapCount(ArticleDto.IncreaseScrapCountRequest(command.articleId.value))

        domainEventPublisher.publish(scrap)
    }
}
