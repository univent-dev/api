package com.univent.api.article.article.application.view

interface ArticleReader {
    fun findById(id: Long): ArticleReadModel?

    fun findAllByCriteria(
        tags: List<String>? = null,
        isFinished: Boolean? = null,
        sortBy: String? = "createdAt",
        keyword: String? = null,
        searchType: SearchType? = null
    ): List<ArticleReadModel>
}
