package com.univent.api.article.tag.application.result

import com.univent.api.article.tag.TagDto
import com.univent.api.common.core.domain.vo.identifier.TagId

data class FindOrCreateTagsResult(
    val tagIds: Set<TagId>
) {
    fun toDto(): TagDto.FindOrCreateTagsResponse {
        return TagDto.FindOrCreateTagsResponse(
            tagIds = tagIds.map { it.value }.toSet()
        )
    }
}
