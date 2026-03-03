package com.univent.api.article.article.infrastructure

import com.univent.api.article.article.domain.Article
import com.univent.api.common.core.domain.vo.identifier.ArticleId
import com.univent.api.common.core.domain.vo.identifier.OrganizationId
import com.univent.api.common.core.domain.vo.identifier.TagId
import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "article")
class ArticleEntity(
    @Id
    val id: Long,

    @Column(nullable = false)
    val title: String,

    @Column(nullable = false)
    val organizationId: Long,

    @Column(nullable = false)
    val organization: String,

    @Column(nullable = false)
    val location: String,

    @Column(nullable = false, length = 2047)
    val description: String,

    @Column(nullable = true)
    val registrationUrl: String? = null,

    @Column(nullable = false)
    val startAt: Instant,

    @Column(nullable = false)
    val endAt: Instant,

    @Column(nullable = true)
    val registrationStartAt: Instant? = null,

    @Column(nullable = true)
    val registrationEndAt: Instant? = null,

    @Column(nullable = false)
    val scrapCount: Int,

    @Column(nullable = false)
    val viewCount: Int,

    @ElementCollection
    @CollectionTable(name = "article_tag", joinColumns = [JoinColumn(name = "article_id")])
    @Column(name = "tag_id")
    val tagIds: Set<Long> = mutableSetOf(),

    @Column(nullable = false, updatable = false)
    val createdAt: Instant,

    @Column(nullable = false)
    val updatedAt: Instant
) {
    companion object {
        fun fromDomain(article: Article): ArticleEntity {
            return ArticleEntity(
                id = article.id.value,
                title = article.title,
                organizationId = article.organizationId.value,
                organization = article.organization,
                location = article.location,
                description = article.description,
                registrationUrl = article.registrationUrl,
                startAt = article.startAt,
                endAt = article.endAt,
                registrationStartAt = article.registrationStartAt,
                registrationEndAt = article.registrationEndAt,
                tagIds = article.tagIds.map { it.value }.toSet(),
                scrapCount = article.scrapCount,
                viewCount = article.viewCount,
                createdAt = article.createdAt,
                updatedAt = article.updatedAt
            )
        }
    }

    fun toDomain(): Article {
        return Article.of(
            id = ArticleId(id),
            title = title,
            organization = organization,
            location = location,
            description = description,
            startAt = startAt,
            endAt = endAt,
            organizationId = OrganizationId(organizationId),
            registrationUrl = registrationUrl,
            registrationStartAt = registrationStartAt,
            registrationEndAt = registrationEndAt,
            tagIds = tagIds.map { TagId(it) },
            scrapCount = scrapCount,
            viewCount = viewCount,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }
}
