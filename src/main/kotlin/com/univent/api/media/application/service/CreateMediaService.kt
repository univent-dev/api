package com.univent.api.media.application.service

import com.univent.api.common.core.domain.IdGenerator
import com.univent.api.common.core.domain.vo.identifier.ArticleId
import com.univent.api.common.core.domain.vo.identifier.MediaId
import com.univent.api.media.application.CreateMediaUseCase
import com.univent.api.media.application.command.CreateMediaCommand
import com.univent.api.media.domain.Media
import com.univent.api.media.domain.MediaStore
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CreateMediaService(
    private val mediaStore: MediaStore,
    private val idGenerator: IdGenerator
) : CreateMediaUseCase {

    @Transactional
    override fun execute(command: CreateMediaCommand) {
        val articleId = command.articleId
        val mediaList = mutableListOf<Media>()

        command.thumbnailInfo?.let {
            mediaList.add(createMedia(articleId, it.imageUrl, 0))
        }

        command.fileInfoList.forEachIndexed { index, info ->
            mediaList.add(createMedia(articleId, info.imageUrl, index + 1))
        }

        if (mediaList.isNotEmpty()) { mediaStore.saveAll(mediaList) }
    }

    private fun createMedia(articleId: ArticleId, imageUrl: String, order: Int): Media {
        return Media.create(
            id = MediaId(idGenerator.generateId()),
            mediaPath = imageUrl,
            order = order,
            articleId = articleId.value
        )
    }
}
