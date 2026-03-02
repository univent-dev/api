package com.univent.api.article.article.application.service

import com.univent.api.article.article.application.DecreaseScrapCountUseCase
import com.univent.api.article.article.application.IncreaseScrapCountUseCase
import com.univent.api.article.article.application.command.DecreaseScrapCountCommand
import com.univent.api.article.article.application.command.IncreaseScrapCountCommand
import com.univent.api.article.article.domain.ArticleErrorCode
import com.univent.api.article.article.domain.ArticleStore
import com.univent.api.common.exception.CustomException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DecreaseScrapCountService(
    private val articleStore: ArticleStore
) : DecreaseScrapCountUseCase {
    @Transactional
    override fun execute(command: DecreaseScrapCountCommand) {
        val article = articleStore.loadById(command.id)
            ?: throw CustomException(ArticleErrorCode.ARTICLE_NOT_FOUND)

        article.decreaseScrapCount()

        articleStore.save(article)
    }
}
