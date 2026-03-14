package com.univent.api.article.scrap.domain

import com.univent.api.common.core.domain.vo.identifier.ArticleId
import com.univent.api.common.core.domain.vo.identifier.UserId

interface ScrapStore {
    fun save(scrap: Scrap)
    fun deleteByArticleIdAndUserId(articleId: ArticleId, userId: UserId)
    fun loadByArticleIdAndUserId(articleId: ArticleId, userId: UserId): Scrap?
}
