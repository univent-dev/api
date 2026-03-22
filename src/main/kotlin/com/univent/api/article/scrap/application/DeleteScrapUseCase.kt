package com.univent.api.article.scrap.application

import com.univent.api.article.scrap.application.command.DeleteScrapCommand

interface DeleteScrapUseCase {
    fun execute(command: DeleteScrapCommand)
}
