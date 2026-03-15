package com.univent.api.media.application

import com.univent.api.media.application.command.GeneratePresignedUrlCommand
import com.univent.api.media.application.result.GeneratePresignedUrlResult

interface GeneratePresignedUrlUseCase {
    fun execute(command: GeneratePresignedUrlCommand): GeneratePresignedUrlResult
}
