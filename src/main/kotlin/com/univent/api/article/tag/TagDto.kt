package com.univent.api.article.tag

sealed interface TagDto {
    data class FindOrCreateTagsRequest(
        val tagNames: Set<String>
    ) : TagDto

    data class FindOrCreateTagsResponse(
        val tagIds: Set<Long>
    ) : TagDto
}
