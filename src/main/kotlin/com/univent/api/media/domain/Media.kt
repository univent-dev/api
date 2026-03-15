package com.univent.api.media.domain

import com.univent.api.common.core.domain.AggregateRoot
import com.univent.api.common.core.domain.vo.identifier.MediaId
import com.univent.api.common.exception.CustomException
import java.time.Instant

class Media private constructor(
    id: MediaId,
    mediaPath: String,
    order: Int,
    val articleId: Long,
    val createdAt: Instant = Instant.now(),
    updatedAt: Instant = Instant.now()
): AggregateRoot<MediaId>(id) {
    var mediaPath = mediaPath; private set
    var order = order; private set
    var updatedAt = updatedAt; private set

    init { validate() }

    companion object {
        fun create(
            id: MediaId,
            mediaPath: String,
            order: Int,
            articleId: Long
        ): Media {
            return Media(
                id = id,
                mediaPath = mediaPath,
                order = order,
                articleId = articleId
            )
        }

        fun of(
            id: MediaId,
            mediaPath: String,
            order: Int,
            articleId: Long,
            createdAt: Instant,
            updatedAt: Instant
        ): Media = Media(id, mediaPath, order, articleId, createdAt, updatedAt)
    }

    private fun validate() {
        require(mediaPath.isNotBlank()) { throw CustomException(MediaErrorCode.MEDIA_PATH_EMPTY) }
        require(isValidMediaPath(mediaPath)) { throw CustomException(MediaErrorCode.MEDIA_INVALID_PATH) }

        require(order >= 0) { throw CustomException(MediaErrorCode.MEDIA_ORDER_EMPTY) }
        require(articleId > 0) { throw CustomException(MediaErrorCode.MEDIA_ARTICLE_ID_EMPTY) }
    }

    private fun isValidMediaPath(path: String): Boolean {
        val allowedExtensions = listOf("png", "jpg", "jpeg", "img").joinToString("|")
        val regex = Regex("^https://[^/]+/images/[^/]+/[^/]+\\.($allowedExtensions)$")
        return regex.matches(path)
    }
}
