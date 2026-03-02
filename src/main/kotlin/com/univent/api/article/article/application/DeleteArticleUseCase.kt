package com.univent.api.article.article.application

import com.univent.api.article.article.application.command.DeleteArticleCommand

interface DeleteArticleUseCase {
    fun execute(command: DeleteArticleCommand)
}
