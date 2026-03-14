package com.univent.api.article.article

import com.univent.api.article.article.application.view.ArticleDetailView
import com.univent.api.article.article.application.view.ArticleView
import java.time.Instant

sealed interface ArticleDto {
    data class IncreaseScrapCountRequest(
        val articleId: Long,
    ) : ArticleDto

    data class DecreaseScrapCountRequest(
        val articleId: Long,
    ) : ArticleDto

    data class GetArticleByIdRequest(
        val articleId: Long
    ) : ArticleDto

    data class GetArticlesByIdsRequest(
        val articleIds: List<Long>
    ) : ArticleDto

    data class GetArticleByIdResponse(
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
    ) : ArticleDto {
        companion object {
            fun fromView(view: ArticleDetailView): GetArticleByIdResponse {
                return GetArticleByIdResponse(
                    id = view.id,
                    title = view.title,
                    organization = view.organization,
                    scrapCount = view.scrapCount,
                    viewCount = view.viewCount,
                    startAt = view.startAt,
                    endAt = view.endAt,
                    registrationStartAt = view.registrationStartAt,
                    registrationEndAt = view.registrationEndAt,
                    description = view.description,
                    location = view.location,
                    registrationUrl = view.registrationUrl,
                    tags = view.tags,
                    thumbnailPath = view.thumbnailPath,
                    imagePaths = view.imagePaths
                )
            }
        }
    }

    data class ArticleSummary(
        val id: Long,
        val title: String,
        val organization: String,
        val scrapCount: Int,
        val viewCount: Int,
        val startAt: Instant,
        val endAt: Instant,
        val thumbnailPath: String
    ) : ArticleDto {
        companion object {
            fun fromView(view: ArticleView): ArticleSummary {
                return ArticleSummary(
                    id = view.id,
                    title = view.title,
                    organization = view.organization,
                    scrapCount = view.scrapCount,
                    viewCount = view.viewCount,
                    startAt = view.startAt,
                    endAt = view.endAt,
                    thumbnailPath = view.thumbnailPath
                )
            }
        }
    }
}
