package com.univent.api.article.tag.application

import com.univent.api.article.tag.application.command.FindOrCreateTagsCommand
import com.univent.api.article.tag.application.result.FindOrCreateTagsResult

interface FindOrCreateTagsUseCase {
    fun execute(command: FindOrCreateTagsCommand): FindOrCreateTagsResult
}
