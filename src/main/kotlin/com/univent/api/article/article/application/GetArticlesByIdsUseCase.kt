package com.univent.api.article.article.application

import com.univent.api.article.article.application.query.GetArticlesByIdsQuery
import com.univent.api.article.article.application.view.ArticleView

interface GetArticlesByIdsUseCase {
    fun execute(query: GetArticlesByIdsQuery): List<ArticleView>
}
