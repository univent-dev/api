package com.univent.api.media.presentation.dto

import com.univent.api.media.application.command.FileInfo

data class FileInfoDto(val fileName: String, val mimeType: String) {
    fun toCommand() = FileInfo(
        fileName = fileName,
        mimeType = mimeType
    )
}
