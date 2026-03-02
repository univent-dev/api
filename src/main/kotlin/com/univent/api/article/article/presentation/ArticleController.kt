package com.univent.api.article.article.presentation

import com.univent.api.article.article.application.CreateArticleUseCase
import com.univent.api.article.article.application.DeleteArticleUseCase
import com.univent.api.article.article.application.GetArticleByIdUseCase
import com.univent.api.article.article.application.GetArticlesUseCase
import com.univent.api.article.article.application.UpdateArticleUseCase
import com.univent.api.article.article.application.command.DeleteArticleCommand
import com.univent.api.article.article.application.query.GetArticleByIdQuery
import com.univent.api.article.article.application.query.GetArticlesQuery
import com.univent.api.article.article.application.view.ArticleDetailView
import com.univent.api.article.article.application.view.ArticleView
import com.univent.api.article.article.application.view.SearchType
import com.univent.api.article.article.presentation.dto.CreateArticleDto
import com.univent.api.article.article.presentation.dto.UpdateArticleDto
import com.univent.api.common.core.domain.vo.identifier.ArticleId
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/article")
class ArticleController(
    private val createArticleUseCase: CreateArticleUseCase,
    private val updateArticleUseCase: UpdateArticleUseCase,
    private val deleteArticleUseCase: DeleteArticleUseCase,
    private val getArticleByIdUseCase: GetArticleByIdUseCase,
    private val getArticlesUseCase: GetArticlesUseCase
) {
    @PostMapping
    fun createArticle(@RequestBody reqDto: CreateArticleDto.Req): CreateArticleDto.Res {
        val command = reqDto.toCommand(1L)
        val result = createArticleUseCase.execute(command)

        return CreateArticleDto.Res.fromResult(result)
    }

    @PatchMapping("/{id}")
    fun updateArticle(
        @PathVariable id: Long,
        @RequestBody reqDto: UpdateArticleDto.Req
    ) {
        val command = reqDto.toCommand(id)
        updateArticleUseCase.execute(command)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) {
        val command = DeleteArticleCommand(id = ArticleId(id))
        deleteArticleUseCase.execute(command)
    }

    @GetMapping("/{id}")
    fun getArticleDetail(@PathVariable id: Long): ArticleDetailView {
        val query = GetArticleByIdQuery(id)
        return getArticleByIdUseCase.execute(query)
    }

    @GetMapping
    fun getArticles(
        @RequestParam(required = false) tags: List<String>?,
        @RequestParam(required = false) isFinished: Boolean?,
        @RequestParam(required = false) sortBy: String?,
        @RequestParam(required = false) keyword: String?,
        @RequestParam(defaultValue = "ALL") searchType: SearchType
    ): List<ArticleView> {
        val query = GetArticlesQuery(
            tags = tags,
            isFinished = isFinished,
            sortBy = sortBy,
            keyword = keyword,
            searchType = searchType
        )

        return getArticlesUseCase.execute(query)
    }
}
