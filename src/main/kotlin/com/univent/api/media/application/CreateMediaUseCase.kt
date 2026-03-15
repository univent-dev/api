package com.univent.api.media.application

import com.univent.api.media.application.command.CreateMediaCommand

interface CreateMediaUseCase {
        fun execute(command: CreateMediaCommand)
}
