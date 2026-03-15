package com.univent.api.media.infrastructure

import com.univent.api.common.core.domain.vo.identifier.MediaId
import com.univent.api.media.domain.Media
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.Instant

@Entity
@Table(name = "media")
class MediaEntity(
    @Id
    val id: Long = 0L,

    @Column(nullable = false)
    val mediaPath: String,

    @Column(nullable = false)
    val order: Int,

    @Column(nullable = false)
    val articleId: Long,

    @Column(nullable = false, updatable = false)
    val createdAt: Instant,

    @Column(nullable = false)
    val updatedAt: Instant
) {
    fun toDomain() = Media.of(MediaId(id), mediaPath, order, articleId, createdAt, updatedAt)

    companion object {
        fun fromDomain(media: Media) = MediaEntity(
            id = media.id.value,
            mediaPath = media.mediaPath,
            order = media.order,
            articleId = media.articleId,
            createdAt = media.createdAt,
            updatedAt = media.updatedAt
        )
    }
}
