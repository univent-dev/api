package com.univent.api.article.article.application

import com.univent.api.article.article.application.command.UpdateArticleCommand

interface UpdateArticleUseCase {
    fun execute(command: UpdateArticleCommand)
}
