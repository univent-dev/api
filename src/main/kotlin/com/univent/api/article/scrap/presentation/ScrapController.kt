package com.univent.api.article.scrap.presentation

import com.univent.api.article.article.ArticleDto
import com.univent.api.article.scrap.application.AddScrapUseCase
import com.univent.api.article.scrap.application.DeleteScrapUseCase
import com.univent.api.article.scrap.application.GetScrapStatusUseCase
import com.univent.api.article.scrap.application.GetScrappedArticlesUseCase
import com.univent.api.article.scrap.application.command.DeleteScrapCommand
import com.univent.api.article.scrap.application.query.GetScrapStatusQuery
import com.univent.api.article.scrap.presentation.dto.AddScrapDto
import com.univent.api.article.scrap.presentation.dto.GetScrappedArticlesDto
import com.univent.api.common.core.domain.vo.identifier.ArticleId
import com.univent.api.common.core.domain.vo.identifier.UserId
import com.univent.api.common.core.presentation.UserPayload
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/scraps")
class ScrapController(
    private val addScrapUseCase: AddScrapUseCase,
    private val deleteScrapUseCase: DeleteScrapUseCase,
    private val getScrapStatusUseCase: GetScrapStatusUseCase,
    private val getScrappedArticlesUseCase: GetScrappedArticlesUseCase
) {
    @PostMapping("/{id}")
    fun addScrap(
        @PathVariable("id") articleId: Long,
        @AuthenticationPrincipal user: UserPayload
    ): ResponseEntity<Unit> {
        val command = AddScrapDto.Req(articleId).toCommand(user.id)
        addScrapUseCase.execute(command)

        return ResponseEntity.status(HttpStatus.OK).build()
    }

    @DeleteMapping("/{id}")
    fun deleteScrap(
        @PathVariable("id") articleId: Long,
        @AuthenticationPrincipal user: UserPayload
    ) {
        val command = DeleteScrapCommand(UserId(user.id), ArticleId(articleId))
        deleteScrapUseCase.execute(command)
    }

    @GetMapping
    fun getScrappedArticles(
        @AuthenticationPrincipal user: UserPayload,
        @ModelAttribute reqDto: GetScrappedArticlesDto.Req
    ): List<ArticleDto.ArticleSummary> {
        val query = reqDto.toQuery(user.id)
        return getScrappedArticlesUseCase.execute(query)
    }

    @GetMapping("/article/{id}")
    fun getScrapStatus(
        @PathVariable("id") articleId: Long,
        @AuthenticationPrincipal user: UserPayload
    ): Boolean {
        val query = GetScrapStatusQuery(articleId, user.id)
        return getScrapStatusUseCase.execute(query)
    }
}
