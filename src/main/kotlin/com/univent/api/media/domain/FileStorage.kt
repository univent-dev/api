package com.univent.api.media.domain

interface FileStorage {
    fun upload(articleId: Long, fileName: String, mimeType: String): FileUploadResult
    fun delete(url: String)

    data class FileUploadResult(
        val presignedUrl: String,
        val imageUrl: String
    )
}
