package com.univent.api.article.scrap.application

import com.univent.api.article.scrap.application.command.AddScrapCommand

interface AddScrapUseCase {
    fun execute(command: AddScrapCommand)
}
