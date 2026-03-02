package com.univent.api.article.article.presentation

import com.univent.api.article.article.application.CreateArticleUseCase
import com.univent.api.article.article.application.DeleteArticleUseCase
import com.univent.api.article.article.application.UpdateArticleUseCase
import com.univent.api.article.article.application.command.DeleteArticleCommand
import com.univent.api.article.article.presentation.dto.CreateArticleDto
import com.univent.api.article.article.presentation.dto.UpdateArticleDto
import com.univent.api.common.core.domain.vo.identifier.ArticleId
import com.univent.api.common.core.presentation.OrganizationPayload
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/organization/article")
class ArticleOrganizationController(
    private val createArticleUseCase: CreateArticleUseCase,
    private val updateArticleUseCase: UpdateArticleUseCase,
    private val deleteArticleUseCase: DeleteArticleUseCase
) {
    @PostMapping
    @PreAuthorize("hasRole('ORGANIZATION')")
    fun createArticle(
        @AuthenticationPrincipal organization: OrganizationPayload,
        @RequestBody reqDto: CreateArticleDto.Req
    ): CreateArticleDto.Res {
        val command = reqDto.toCommand(organizationId = organization.id)
        val result = createArticleUseCase.execute(command)

        return CreateArticleDto.Res.fromResult(result)
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ORGANIZATION')")
    fun updateArticle(
        @AuthenticationPrincipal organization: OrganizationPayload,
        @PathVariable id: Long,
        @RequestBody reqDto: UpdateArticleDto.Req
    ) {
        val command = reqDto.toCommand(id)
        updateArticleUseCase.execute(command)
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ORGANIZATION')")
    fun delete(
        @AuthenticationPrincipal organization: OrganizationPayload,
        @PathVariable id: Long
    ) {
        val command = DeleteArticleCommand(ArticleId(id))
        deleteArticleUseCase.execute(command)
    }
}
