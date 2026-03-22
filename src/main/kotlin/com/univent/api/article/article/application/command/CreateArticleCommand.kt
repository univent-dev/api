package com.univent.api.article.article.application.command

import com.univent.api.common.core.domain.vo.identifier.OrganizationId
import java.time.Instant

data class CreateArticleCommand(
    val title: String,
    val organizationId: OrganizationId,
    val organization: String,
    val description: String,
    val location: String,
    val startAt: Instant,
    val endAt: Instant,
    val tagNames: List<String>,
    val registrationUrl: String? = null,
    val registrationStartAt: Instant? = null,
    val registrationEndAt: Instant? = null
)
