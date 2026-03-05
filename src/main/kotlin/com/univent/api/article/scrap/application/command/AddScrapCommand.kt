package com.univent.api.article.scrap.application.command

import com.univent.api.common.core.domain.vo.identifier.ArticleId
import com.univent.api.common.core.domain.vo.identifier.UserId

data class AddScrapCommand(
    val userId: UserId,
    val articleId: ArticleId,
)
