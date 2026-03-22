package com.univent.api.article.article.application.command

import com.univent.api.common.core.domain.vo.identifier.ArticleId
import java.time.Instant

data class UpdateArticleCommand(
    val id: ArticleId,
    val title: String? = null,
    val organization: String? = null,
    val description: String? = null,
    val location: String? = null,
    val startAt: Instant? = null,
    val endAt: Instant? = null,
    val registrationUrl: String? = null,
    val registrationStartAt: Instant? = null,
    val registrationEndAt: Instant? = null,
    val tagNames: List<String>? = null
)
