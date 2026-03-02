package com.univent.api.article.article.application.command

import com.univent.api.common.core.domain.vo.identifier.ArticleId

data class DeleteArticleCommand(
    val id: ArticleId
)
