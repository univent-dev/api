package com.univent.api.media.application.service

import com.univent.api.common.core.domain.IdGenerator
import com.univent.api.common.core.domain.vo.identifier.ArticleId
import com.univent.api.common.core.domain.vo.identifier.MediaId
import com.univent.api.media.application.UpdateMediaUseCase
import com.univent.api.media.application.command.UpdateMediaCommand
import com.univent.api.media.domain.Media
import com.univent.api.media.domain.MediaStore
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
class UpdateMediaService(
    private val mediaStore: MediaStore,
    private val idGenerator: IdGenerator
) : UpdateMediaUseCase {
    @Transactional
    override fun execute(command: UpdateMediaCommand) {
        val articleId = command.articleId

        // 1. 기존 데이터 로드 (Domain Reconstitution)
        val existingMedias = mediaStore.loadByArticleId(articleId.value)
        val existingMap = existingMedias.associateBy { it.order }

        val incomingMediaList = mutableListOf<Media>()
        val incomingOrders = mutableSetOf<Int>()

        // 2. 썸네일 처리 (order 0)
        command.thumbnailInfo?.let {
            val order = 0
            incomingOrders.add(order)
            incomingMediaList.add(
                mapToMedia(articleId, it.imageUrl, order, existingMap[order])
            )
        }

        // 3. 파일 리스트 처리 (order 1..N)
        command.fileInfoList.forEachIndexed { index, info ->
            val order = index + 1
            incomingOrders.add(order)
            incomingMediaList.add(
                mapToMedia(articleId, info.imageUrl, order, existingMap[order])
            )
        }

        // 4. 삭제 대상 식별 (기존 데이터 중 새 데이터에 없는 order 제거)
        val toDeleteIds = existingMedias
            .filter { it.order !in incomingOrders }
            .map { it.id }

        if (toDeleteIds.isNotEmpty()) {
            mediaStore.deleteByIds(toDeleteIds)
        }

        // 5. 일괄 업데이트 및 저장
        if (incomingMediaList.isNotEmpty()) {
            mediaStore.updateAll(incomingMediaList)
        }
    }

    private fun mapToMedia(
        articleId: ArticleId,
        imageUrl: String,
        order: Int,
        existingMedia: Media?
    ): Media {
        return if (existingMedia != null) {
            // 기존 데이터가 있다면 업데이트 (of 메서드 활용하여 ID 유지)
            Media.of(
                id = existingMedia.id,
                mediaPath = imageUrl,
                order = order,
                articleId = articleId.value,
                createdAt = existingMedia.createdAt,
                updatedAt = Instant.now()
            )
        } else {
            // 신규 데이터라면 생성
            Media.create(
                id = MediaId(idGenerator.generateId()),
                mediaPath = imageUrl,
                order = order,
                articleId = articleId.value
            )
        }
    }
}
