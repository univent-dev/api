package com.univent.api.article.article.application.view

import java.time.Instant

data class ArticleView(
    val id: Long,
    val title: String,
    val organization: String,
    val thumbnailPath: String,
    val scrapCount: Int,
    val viewCount: Int,
    val tags: List<String>,
    val startAt: Instant,
    val endAt: Instant,
    val registrationStartAt: Instant? = null,
    val registrationEndAt: Instant? = null
)
