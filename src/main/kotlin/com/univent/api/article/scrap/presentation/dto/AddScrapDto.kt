package com.univent.api.article.scrap.presentation.dto

import com.univent.api.article.scrap.application.command.AddScrapCommand
import com.univent.api.common.core.domain.vo.identifier.ArticleId
import com.univent.api.common.core.domain.vo.identifier.UserId

sealed interface AddScrapDto {
    data class Req(
        val articleId: Long
    ) {
        fun toCommand(userId: Long) = AddScrapCommand(UserId(userId), ArticleId(articleId))
    }
}

