package com.univent.api.article.tag

interface TagApi {
    fun findOrCreateTags(request: TagDto.FindOrCreateTagsRequest): TagDto.FindOrCreateTagsResponse
}
