package com.univent.api.article.article.application.query

import com.univent.api.article.article.application.view.SearchType

data class GetArticlesQuery(
    val tags: List<String>?,
    val isFinished: Boolean?,
    val sortBy: String?,
    val keyword: String?,
    val searchType: SearchType?,
    val page: Int = 0,
    val size: Int = 10
)
