package com.univent.api.media.presentation.dto

import com.univent.api.media.application.command.MediaInfo

data class MediaInfoDto(val imageUrl: String) {
    fun toCommand(): MediaInfo = MediaInfo(imageUrl)
}
