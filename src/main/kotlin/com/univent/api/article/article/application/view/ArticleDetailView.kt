package com.univent.api.article.article.application.view

import java.time.Instant

data class ArticleDetailView(
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
    val tags: List<String>,
    val thumbnailPath: String,
    val imagePaths: List<String>
)
