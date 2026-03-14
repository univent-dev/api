package com.univent.api.article.scrap.domain

import com.univent.api.article.scrap.domain.event.ScrapAddedEvent
import com.univent.api.article.scrap.domain.event.ScrapDeletedEvent
import com.univent.api.common.core.domain.AggregateRoot
import com.univent.api.common.core.domain.vo.identifier.ArticleId
import com.univent.api.common.core.domain.vo.identifier.ScrapId
import com.univent.api.common.core.domain.vo.identifier.UserId
import java.time.Instant

class Scrap private constructor(
    id: ScrapId,
    val articleId: ArticleId,
    val userId: UserId,
    val createdAt: Instant = Instant.now()
) : AggregateRoot<ScrapId>(id) {


    init { validate() }

    private fun validate() {}

    companion object {
        fun create(id: ScrapId, articleId: ArticleId, userId: UserId, tags: List<String>): Scrap {
            val scrap = Scrap(id, articleId, userId, Instant.now())
            scrap.addDomainEvent(
                ScrapAddedEvent(
                    userId = userId.value,
                    articleId = articleId.value,
                    tags = tags
                )
            )

            return scrap
        }

        fun of(id: ScrapId, articleId: ArticleId, userId: UserId, createdAt: Instant): Scrap {
            return Scrap(id, articleId, userId, createdAt)
        }
    }

    fun delete(tags: List<String>) {
        addDomainEvent(ScrapDeletedEvent(userId.value, articleId.value, tags))
    }

}
