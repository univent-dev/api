package com.univent.api.article.article.application.service

import com.univent.api.article.article.application.IncreaseScrapCountUseCase
import com.univent.api.article.article.application.command.IncreaseScrapCountCommand
import com.univent.api.article.article.domain.ArticleErrorCode
import com.univent.api.article.article.domain.ArticleStore
import com.univent.api.common.exception.CustomException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class IncreaseScrapCountService(
    private val articleStore: ArticleStore
) : IncreaseScrapCountUseCase {

    @Transactional
    override fun execute(command: IncreaseScrapCountCommand) {
        val article = articleStore.loadById(command.id)
            ?: throw CustomException(ArticleErrorCode.ARTICLE_NOT_FOUND)

        article.increaseScrapCount()

        articleStore.save(article)
    }
}
