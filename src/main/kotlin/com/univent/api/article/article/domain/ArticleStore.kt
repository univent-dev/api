package com.univent.api.article.article.domain

import com.univent.api.common.core.domain.vo.identifier.ArticleId

interface ArticleStore {
    fun save(article: Article)
    fun loadById(id: ArticleId): Article?
    fun deleteById(id: ArticleId)
}
