package com.univent.api.article.article.presentation.dto

sealed interface DeleteArticleDto {
    data class Req(
        val id: Long,
        val organizationId: Long
    )
}
