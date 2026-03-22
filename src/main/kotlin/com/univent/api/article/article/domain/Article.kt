package com.univent.api.article.article.domain

import com.univent.api.common.core.domain.AggregateRoot
import com.univent.api.common.core.domain.vo.identifier.ArticleId
import com.univent.api.common.core.domain.vo.identifier.OrganizationId
import com.univent.api.common.core.domain.vo.identifier.TagId
import com.univent.api.common.exception.CustomException
import java.time.Instant

class Article private constructor(
    id: ArticleId,
    title: String,
    organization: String,
    location: String,
    description: String,
    startAt: Instant,
    endAt: Instant,
    registrationUrl: String? = null,
    registrationStartAt: Instant? = null,
    registrationEndAt: Instant? = null,
    scrapCount: Int = 0,
    viewCount: Int = 0,
    mediaIds: List<Long> = emptyList(),
    tagIds: List<TagId> = emptyList(),
    val organizationId: OrganizationId,
    val createdAt: Instant,
    updatedAt: Instant = Instant.now()
) : AggregateRoot<ArticleId>(id) {
    var title = title; private set
    var organization = organization; private set
    var location = location; private set
    var description = description; private set
    var startAt = startAt; private set
    var endAt = endAt; private set
    var registrationUrl = registrationUrl; private set
    var registrationStartAt = registrationStartAt; private set
    var registrationEndAt = registrationEndAt; private set
    var scrapCount = scrapCount; private set
    var viewCount = viewCount; private set
    var mediaIds = mediaIds; private set
    var tagIds = tagIds; private set
    var updatedAt = updatedAt; private set

    init { validate() }

    companion object {
        fun create(
            id: ArticleId,
            title: String,
            organization: String,
            location: String,
            description: String,
            startAt: Instant,
            endAt: Instant,
            organizationId: OrganizationId,
            registrationUrl: String? = null,
            registrationStartAt: Instant? = null,
            registrationEndAt: Instant? = null,
            mediaIds: List<Long> = emptyList(),
            tagIds: List<TagId> = emptyList()
        ) = Article(
            id = id,
            title = title,
            organization = organization,
            location = location,
            description = description,
            startAt = startAt,
            endAt = endAt,
            organizationId = organizationId,
            registrationUrl = registrationUrl,
            registrationStartAt = registrationStartAt,
            registrationEndAt = registrationEndAt,
            mediaIds = mediaIds,
            tagIds = tagIds,
            createdAt = Instant.now()
        )

        fun of(
            id: ArticleId,
            title: String,
            organization: String,
            location: String,
            description: String,
            startAt: Instant,
            endAt: Instant,
            organizationId: OrganizationId,
            registrationUrl: String? = null,
            registrationStartAt: Instant? = null,
            registrationEndAt: Instant? = null,
            scrapCount: Int = 0,
            viewCount: Int = 0,
            mediaIds: List<Long> = emptyList(),
            tagIds: List<TagId> = emptyList(),
            createdAt: Instant,
            updatedAt: Instant
        ) = Article(
            id = id,
            title = title,
            organization = organization,
            location = location,
            description = description,
            startAt = startAt,
            endAt = endAt,
            organizationId = organizationId,
            registrationUrl = registrationUrl,
            registrationStartAt = registrationStartAt,
            registrationEndAt = registrationEndAt,
            scrapCount = scrapCount,
            viewCount = viewCount,
            mediaIds = mediaIds,
            tagIds = tagIds,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }

    private fun validate() {
        require(title.isNotBlank()) { throw CustomException(ArticleErrorCode.ARTICLE_TITLE_EMPTY) }
        require(organization.isNotBlank()) { throw CustomException(ArticleErrorCode.ARTICLE_ORGANIZATION_EMPTY) }
        require(location.isNotBlank()) { throw CustomException(ArticleErrorCode.ARTICLE_LOCATION_EMPTY) }

        require(startAt.isBefore(endAt)) { throw CustomException(ArticleErrorCode.ARTICLE_START_AT_EXCEEDS_END_AT) }

        require(scrapCount >= 0) { throw CustomException(ArticleErrorCode.ARTICLE_SCRAP_COUNT_NEGATIVE) }
        require(viewCount >= 0) { throw CustomException(ArticleErrorCode.ARTICLE_VIEW_COUNT_NEGATIVE) }

        require(mediaIds.size <= 10) { throw CustomException(ArticleErrorCode.ARTICLE_MEDIA_MAX_IMAGES_EXCEEDED) }
    }

    fun update(
        title: String? = null,
        organization: String? = null,
        location: String? = null,
        description: String? = null,
        registrationUrl: String? = null,
        startAt: Instant? = null,
        endAt: Instant? = null,
        registrationStartAt: Instant? = null,
        registrationEndAt: Instant? = null
    ) {
        title?.let { this.title = it }
        organization?.let { this.organization = it }
        location?.let { this.location = it }
        description?.let { this.description = it }
        registrationUrl?.let { this.registrationUrl = it }
        startAt?.let { this.startAt = it }
        endAt?.let { this.endAt = it }

        this.registrationStartAt = registrationStartAt ?: this.registrationStartAt
        this.registrationEndAt = registrationEndAt ?: this.registrationEndAt

        this.updatedAt = Instant.now()
        validate()
    }

    fun increaseScrapCount() {
        this.scrapCount++
        this.updatedAt = Instant.now()
    }

    fun decreaseScrapCount() {
        if (this.scrapCount > 0) {
            this.scrapCount--
            this.updatedAt = Instant.now()
        }
    }

    fun setTagIds(newTagIds: List<TagId>) {
        this.tagIds = newTagIds
        this.updatedAt = Instant.now()
    }
}
