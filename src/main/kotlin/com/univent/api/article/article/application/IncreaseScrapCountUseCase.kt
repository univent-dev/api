package com.univent.api.article.article.application

import com.univent.api.article.article.application.command.IncreaseScrapCountCommand

interface IncreaseScrapCountUseCase {
    fun execute(command: IncreaseScrapCountCommand)
}
