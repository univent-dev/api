package com.univent.api.article.article.application.service

import com.univent.api.article.article.application.DeleteArticleUseCase
import com.univent.api.article.article.application.command.DeleteArticleCommand
import com.univent.api.article.article.domain.ArticleErrorCode
import com.univent.api.article.article.domain.ArticleStore
import com.univent.api.common.exception.CustomException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DeleteArticleService(
    private val articleStore: ArticleStore
) : DeleteArticleUseCase {
    @Transactional
    override fun execute(command: DeleteArticleCommand) {
        articleStore.loadById(command.id)
            ?: throw CustomException(ArticleErrorCode.ARTICLE_NOT_FOUND)

        articleStore.deleteById(command.id)
    }
}
