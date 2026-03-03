package com.univent.api.article.scrap.domain

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

    fun delete(tags: List<String>) {
        addDomainEvent(ScrapDeletedEvent(userId.value, articleId.value, tags))
    }

    companion object {
        fun create(id: ScrapId, articleId: ArticleId, userId: UserId): Scrap {
            return Scrap(id, articleId, userId, Instant.now())
        }

        fun of(id: ScrapId, articleId: ArticleId, userId: UserId, createdAt: Instant): Scrap {
            return Scrap(id, articleId, userId, createdAt)
        }
    }
}
