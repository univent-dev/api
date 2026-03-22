package com.univent.api.media.application.service

import com.univent.api.media.application.GeneratePresignedUrlUseCase
import com.univent.api.media.application.command.GeneratePresignedUrlCommand
import com.univent.api.media.application.command.PresignedUrlInfo
import com.univent.api.media.application.result.GeneratePresignedUrlResult
import com.univent.api.media.domain.FileStorage
import org.springframework.stereotype.Service

@Service
class GeneratePresignedUrlService(
    private val fileStorage: FileStorage
) : GeneratePresignedUrlUseCase {
    override fun execute(command: GeneratePresignedUrlCommand): GeneratePresignedUrlResult {
        val articleIdValue = command.articleId.value

        // 1. 썸네일 정보가 있다면 Presigned URL 생성
        val thumbnailResult = command.thumbnailInfo?.let { info ->
            fileStorage.upload(
                articleId = articleIdValue,
                fileName = info.fileName,
                mimeType = info.mimeType
            )
        }?.let { result ->
            PresignedUrlInfo(
                presignedUrl = result.presignedUrl,
                imageUrl = result.imageUrl
            )
        }

        // 2. 일반 이미지 리스트들에 대해 Presigned URL 생성
        val fileResults = command.fileInfoList.map { info ->
            val result = fileStorage.upload(
                articleId = articleIdValue,
                fileName = info.fileName,
                mimeType = info.mimeType
            )

            PresignedUrlInfo(
                presignedUrl = result.presignedUrl,
                imageUrl = result.imageUrl
            )
        }

        // 3. 결과 객체 조립하여 반환
        return GeneratePresignedUrlResult(
            thumbnailPresignedUrl = thumbnailResult,
            presignedUrls = fileResults
        )
    }
}
