package com.univent.api.article.scrap.infrastructure

import com.univent.api.article.scrap.domain.Scrap
import com.univent.api.common.core.domain.vo.identifier.ArticleId
import com.univent.api.common.core.domain.vo.identifier.ScrapId
import com.univent.api.common.core.domain.vo.identifier.UserId
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import java.time.Instant

@Entity
@Table(
    name = "scrap",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["article_id", "user_id"])
    ]
)
class ScrapEntity(
    @Id
    val id: Long,

    @Column(nullable = false)
    val articleId: Long,

    @Column(nullable = false)
    val userId: Long,

    @Column(nullable = false, updatable = false)
    val createdAt: Instant
) {
    companion object {
        fun fromDomain(scrap: Scrap): ScrapEntity {
            return ScrapEntity(
                id = scrap.id.value,
                articleId = scrap.articleId.value,
                userId = scrap.userId.value,
                createdAt = scrap.createdAt
            )
        }
    }

    fun toDomain(): Scrap {
        return Scrap.of(
            id = ScrapId(id),
            articleId = ArticleId(articleId),
            userId = UserId(userId),
            createdAt = createdAt
        )
    }
}
