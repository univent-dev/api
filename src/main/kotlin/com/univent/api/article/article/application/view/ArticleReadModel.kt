package com.univent.api.article.article.application.view

import java.time.Instant

data class ArticleReadModel(
    val id: Long,
    val title: String,
    val organization: String,
    val scrapCount: Int,
    val viewCount: Int,
    val startAt: Instant,
    val endAt: Instant,
    val registrationStartAt: Instant?,
    val registrationEndAt: Instant?,
    val description: String,
    val location: String,
    val registrationUrl: String?,
    val tagIds: Set<Long>,
    val thumbnailId: Long,
    val imageIds: List<Long>,
) {
    fun toArticleView(thumbnailPath: String, tags: List<String>): ArticleView {
        return ArticleView(
            id = id,
            title = title,
            organization = organization,
            thumbnailPath = thumbnailPath,
            scrapCount = scrapCount,
            viewCount = viewCount,
            tags = tags,
            startAt = startAt,
            endAt = endAt,
            registrationStartAt = registrationStartAt,
            registrationEndAt = registrationEndAt
        )
    }
}
