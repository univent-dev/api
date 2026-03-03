package com.univent.api.article.scrap.infrastructure

import com.univent.api.article.scrap.domain.Scrap
import com.univent.api.article.scrap.domain.ScrapStore
import com.univent.api.common.core.domain.vo.identifier.ArticleId
import com.univent.api.common.core.domain.vo.identifier.UserId
import org.springframework.stereotype.Repository

@Repository
class ScrapStoreImpl(
    private val scrapJpaStore: ScrapJpaStore
): ScrapStore {
    override fun save(scrap: Scrap) {
        val entity = ScrapEntity.fromDomain(scrap)
        scrapJpaStore.save(entity)
    }

    override fun deleteByArticleIdAndUserId(articleId: ArticleId, userId: UserId) {
        scrapJpaStore.deleteByArticleIdAndUserId(articleId.value, userId.value)
    }

    override fun loadByArticleIdAndUserId(articleId: ArticleId, userId: UserId): Scrap? {
        return scrapJpaStore.findByArticleIdAndUserId(articleId.value, userId.value)?.toDomain()
    }
}
