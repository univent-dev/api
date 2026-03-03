package com.univent.api.article.article.presentation.dto

import com.univent.api.article.article.application.command.CreateArticleCommand
import com.univent.api.article.article.application.result.CreateArticleResult
import com.univent.api.common.core.domain.vo.identifier.OrganizationId
import java.time.Instant

sealed interface CreateArticleDto {
    data class Req(
        val title: String,
        val organization: String,
        val location: String,
        val description: String,
        val registrationUrl: String? = null,
        val startAt: Instant,
        val endAt: Instant,
        val registrationStartAt: Instant? = null,
        val registrationEndAt: Instant? = null,
        val tagNames: List<String> = emptyList()
    ) {
        fun toCommand(organizationId: Long) = CreateArticleCommand(
            title = title,
            organizationId = OrganizationId(organizationId),
            organization = organization,
            location = location,
            description = description,
            startAt = startAt,
            endAt = endAt,
            registrationUrl = registrationUrl,
            registrationStartAt = registrationStartAt,
            registrationEndAt = registrationEndAt,
            tagNames = tagNames
        )
    }

    data class Res(
        val articleId: Long
    ) {
        companion object {
            fun fromResult(result: CreateArticleResult): Res = Res(
                articleId = result.articleId.value
            )
        }
    }
}
