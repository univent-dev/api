package com.univent.api.article.article.application

import com.univent.api.article.article.application.command.CreateArticleCommand
import com.univent.api.article.article.application.result.CreateArticleResult

interface CreateArticleUseCase {
    fun execute(command: CreateArticleCommand): CreateArticleResult
}
