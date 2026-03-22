package com.univent.api.media.infrastructure

import com.univent.api.common.core.domain.vo.identifier.MediaId
import com.univent.api.media.domain.Media
import com.univent.api.media.domain.MediaStore
import org.springframework.stereotype.Repository

@Repository
class MediaStoreImpl(
    private val mediaJpaStore: MediaJpaStore
) : MediaStore {
    override fun save(media: Media) {
        val entity = MediaEntity.fromDomain(media)
        mediaJpaStore.save(entity)
    }

    override fun saveAll(mediaList: List<Media>) {
        val entities = mediaList.map { MediaEntity.fromDomain(it) }
        mediaJpaStore.saveAll(entities)
    }

    override fun deleteByIds(mediaIds: List<MediaId>) {
        val ids = mediaIds.map { it.value }
        mediaJpaStore.deleteAllByIdIn(ids)
    }

    override fun loadByArticleId(articleId: Long): List<Media> {
        return mediaJpaStore.findAllByArticleId(articleId)
            .map { it.toDomain() }
    }

    override fun updateAll(mediaList: List<Media>) {
        val entities = mediaList.map { MediaEntity.fromDomain(it) }
        mediaJpaStore.saveAll(entities)
    }
}
