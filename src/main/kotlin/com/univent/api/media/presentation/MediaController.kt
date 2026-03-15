package com.univent.api.media.presentation

import com.univent.api.media.application.CreateMediaUseCase
import com.univent.api.media.application.GeneratePresignedUrlUseCase
import com.univent.api.media.application.UpdateMediaUseCase
import com.univent.api.media.presentation.dto.CreateMediaDto
import com.univent.api.media.presentation.dto.GeneratePresignedUrlDto
import com.univent.api.media.presentation.dto.UpdateMediaDto
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/media")
class MediaController(
    private val generatePresignedUrlUseCase: GeneratePresignedUrlUseCase,
    private val createMediaUseCase: CreateMediaUseCase,
    private val updateMediaUseCase: UpdateMediaUseCase
) {

    @PostMapping("/presigned-url")
    fun generatePresignedUrl(
        @RequestBody request: GeneratePresignedUrlDto.Req
    ): GeneratePresignedUrlDto.Res {
        val command = request.toCommand()
        val result = generatePresignedUrlUseCase.execute(command)
        
        return GeneratePresignedUrlDto.Res.fromResult(result)
    }

    @PostMapping
    fun createMedia(
        @RequestBody request: CreateMediaDto.Req
    ) {
        val command = request.toCommand()
        createMediaUseCase.execute(command)
    }

    @PatchMapping
    fun updateMedia(
        @RequestBody request: UpdateMediaDto.Req
    ) {
        val command = request.toCommand()
        updateMediaUseCase.execute(command)
    }
}
