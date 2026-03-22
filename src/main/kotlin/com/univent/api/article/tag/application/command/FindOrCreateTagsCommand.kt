package com.univent.api.article.tag.application.command

import com.univent.api.article.tag.TagDto

data class FindOrCreateTagsCommand(
    val tagNames: Set<String>
) {
    companion object {
        fun fromDto(dto: TagDto.FindOrCreateTagsRequest): FindOrCreateTagsCommand = FindOrCreateTagsCommand(
            dto.tagNames
        )
    }
}
