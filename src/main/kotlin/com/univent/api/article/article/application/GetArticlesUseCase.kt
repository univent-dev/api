package com.univent.api.article.article.application

import com.univent.api.article.article.application.query.GetArticlesQuery
import com.univent.api.article.article.application.view.ArticleView

interface GetArticlesUseCase {
    fun execute(query: GetArticlesQuery): List<ArticleView>
}
