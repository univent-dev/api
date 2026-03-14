package com.univent.api.article.article.application

import com.univent.api.article.article.ArticleApi
import com.univent.api.article.article.ArticleDto
import com.univent.api.article.article.application.command.DecreaseScrapCountCommand
import com.univent.api.article.article.application.command.IncreaseScrapCountCommand
import com.univent.api.article.article.application.query.GetArticleByIdQuery
import com.univent.api.article.article.application.query.GetArticlesByIdsQuery
import org.springframework.stereotype.Service

@Service
class ArticleApiImpl(
    private val increaseScrapCountUseCase: IncreaseScrapCountUseCase,
    private val decreaseScrapCountUseCase: DecreaseScrapCountUseCase,
    private val getArticleByIdUseCase: GetArticleByIdUseCase,
    private val getArticlesByIdsUseCase: GetArticlesByIdsUseCase
): ArticleApi {
    override fun increaseScrapCount(request: ArticleDto.IncreaseScrapCountRequest) {
        val command = IncreaseScrapCountCommand.fromDto(request)
        increaseScrapCountUseCase.execute(command)
    }

    override fun decreaseScrapCount(request: ArticleDto.DecreaseScrapCountRequest) {
        val command = DecreaseScrapCountCommand.fromDto(request)
        decreaseScrapCountUseCase.execute(command)
    }

    override fun getArticleById(request: ArticleDto.GetArticleByIdRequest): ArticleDto.GetArticleByIdResponse {
        val query = GetArticleByIdQuery.fromDto(request)
        val result = getArticleByIdUseCase.execute(query)

        return ArticleDto.GetArticleByIdResponse.fromView(result)
    }

    override fun getArticlesByIds(request: ArticleDto.GetArticlesByIdsRequest): List<ArticleDto.ArticleSummary> {
        val query = GetArticlesByIdsQuery.fromDto(request)
        val results = getArticlesByIdsUseCase.execute(query)

        return results.map { ArticleDto.ArticleSummary.fromView(it) }
    }
}
