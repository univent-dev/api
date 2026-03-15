package com.univent.api.media.application

import com.univent.api.media.application.command.UpdateMediaCommand

interface UpdateMediaUseCase {
    fun execute(command: UpdateMediaCommand)
}
