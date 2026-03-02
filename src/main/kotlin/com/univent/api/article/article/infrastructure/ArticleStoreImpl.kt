package com.univent.api.article.article.infrastructure

import com.univent.api.article.article.domain.Article
import com.univent.api.common.core.domain.vo.identifier.ArticleId
import com.univent.api.article.article.domain.ArticleStore
import org.springframework.stereotype.Repository

@Repository
class ArticleStoreImpl(
    private val articleJpaRepository: ArticleJpaStore,
) : ArticleStore {
    override fun save(article: Article) {
        val entity = ArticleEntity.fromDomain(article)

        articleJpaRepository.save(entity)
    }

    override fun loadById(id: ArticleId): Article? {
        return articleJpaRepository.findById(id.value).orElse(null).toDomain()
    }

    override fun deleteById(id: ArticleId) {
        articleJpaRepository.deleteById(id.value)
    }
}
