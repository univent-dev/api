package com.univent.api.article.article.presentation.dto

import com.univent.api.article.article.application.command.UpdateArticleCommand
import com.univent.api.common.core.domain.vo.identifier.ArticleId
import java.time.Instant

sealed interface UpdateArticleDto {
    data class Req(
        val title: String? = null,
        val organization: String? = null,
        val location: String? = null,
        val description: String? = null,
        val registrationUrl: String? = null,
        val startAt: Instant? = null,
        val endAt: Instant? = null,
        val registrationStartAt: Instant? = null,
        val registrationEndAt: Instant? = null,
        val tagNames: List<String>? = null
    ) {
        fun toCommand(id: Long) = UpdateArticleCommand(
            id = ArticleId(id),
            title = title,
            organization = organization,
            location = location,
            description = description,
            registrationUrl = registrationUrl,
            startAt = startAt,
            endAt = endAt,
            registrationStartAt = registrationStartAt,
            registrationEndAt = registrationEndAt,
            tagNames = tagNames
        )
    }
}
