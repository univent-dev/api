package com.univent.api.media.domain

import com.univent.api.common.core.domain.vo.identifier.MediaId

interface MediaStore {
    fun save(media: Media)
    fun saveAll(mediaList: List<Media>)
    fun deleteByIds(mediaIds: List<MediaId>)
    fun loadByArticleId(articleId: Long): List<Media>
    fun updateAll(mediaList: List<Media>)
}
