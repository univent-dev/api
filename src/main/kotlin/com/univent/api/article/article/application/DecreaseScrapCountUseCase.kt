package com.univent.api.article.article.application

import com.univent.api.article.article.application.command.DecreaseScrapCountCommand

interface DecreaseScrapCountUseCase {
    fun execute(command: DecreaseScrapCountCommand)
}
