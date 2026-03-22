package com.univent.api.article.scrap.application.service

import com.univent.api.article.article.ArticleApi
import com.univent.api.article.article.ArticleDto
import com.univent.api.article.scrap.application.DeleteScrapUseCase
import com.univent.api.article.scrap.application.command.DeleteScrapCommand
import com.univent.api.article.scrap.domain.ScrapErrorCode
import com.univent.api.article.scrap.domain.ScrapStore
import com.univent.api.common.core.domain.DomainEventPublisher
import com.univent.api.common.exception.CustomException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DeleteScrapService(
    private val scrapStore: ScrapStore,
    private val articleApi: ArticleApi,
    private val domainEventPublisher: DomainEventPublisher
) : DeleteScrapUseCase {
    @Transactional
    override fun execute(command: DeleteScrapCommand) {
        val existingScrap =
            scrapStore.loadByArticleIdAndUserId(command.articleId, command.userId)
                ?: throw CustomException(ScrapErrorCode.SCRAP_NOT_FOUND)

        val article = articleApi.getArticleById(ArticleDto.GetArticleByIdRequest(command.articleId.value))

        existingScrap.delete(article.tags)
        scrapStore.deleteByArticleIdAndUserId(command.articleId, command.userId)

        articleApi.decreaseScrapCount(ArticleDto.DecreaseScrapCountRequest(command.articleId.value))

        domainEventPublisher.publish(existingScrap)
    }
}
