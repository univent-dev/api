package com.univent.api.article.tag.application

import com.univent.api.article.tag.TagApi
import com.univent.api.article.tag.TagDto
import com.univent.api.article.tag.application.command.FindOrCreateTagsCommand
import org.springframework.stereotype.Service

@Service
class TagApiImpl(
    private val findOrCreateTagsUseCase: FindOrCreateTagsUseCase
): TagApi {

    override fun findOrCreateTags(request: TagDto.FindOrCreateTagsRequest): TagDto.FindOrCreateTagsResponse {
        val command = FindOrCreateTagsCommand.fromDto(request)

        return findOrCreateTagsUseCase.execute(command).toDto()
    }
}
