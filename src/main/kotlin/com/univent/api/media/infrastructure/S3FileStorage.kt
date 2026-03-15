package com.univent.api.media.infrastructure

import com.univent.api.media.domain.FileStorage
import com.univent.api.media.infrastructure.properties.AwsS3Properties
import org.springframework.stereotype.Component
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest
import java.time.Duration
import java.util.UUID

@Component
class S3FileStorage(
    private val s3Client: S3Client,
    private val s3Presigner: S3Presigner,
    private val properties: AwsS3Properties
) : FileStorage {
    override fun upload(articleId: Long, fileName: String, mimeType: String): FileStorage.FileUploadResult {
        val objectKey = generateKey(fileName, articleId)
        val presignedUrl = generatePresignedUrl(mimeType, objectKey)
        val imageUrl = generateImageUrl(objectKey)

        return FileStorage.FileUploadResult(presignedUrl, imageUrl)
    }

    override fun delete(url: String) {
        // TODO: URL에서 Key 추출 후 s3Client.deleteObject 호출 로직
    }

    private fun generateKey(fileName: String, articleId: Long): String {
        val extension = fileName.substringAfterLast('.', "")
        val uuid = UUID.randomUUID()
        return "images/$articleId/$uuid.$extension"
    }

    private fun generateImageUrl(key: String): String = "${properties.cloudfrontDomain}/$key"

    private fun generatePresignedUrl(mimeType: String, objectKey: String): String {
        val putObjectRequest = PutObjectRequest.builder()
            .key(objectKey)
            .contentType(mimeType)
            .build()

        val presignRequest = PutObjectPresignRequest.builder()
            .signatureDuration(Duration.ofHours(1))
            .putObjectRequest(putObjectRequest)
            .build()

        return s3Presigner.presignPutObject(presignRequest).url().toString()
    }
}