package com.univent.api.article.article.application

import com.univent.api.article.article.application.query.GetArticleByIdQuery
import com.univent.api.article.article.application.view.ArticleDetailView

interface GetArticleByIdUseCase {
    fun execute(query: GetArticleByIdQuery): ArticleDetailView
}
