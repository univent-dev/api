package com.univent.api.article.article.application.service

import com.univent.api.article.article.application.UpdateArticleUseCase
import com.univent.api.article.article.application.command.UpdateArticleCommand
import com.univent.api.article.article.domain.ArticleErrorCode
import com.univent.api.article.article.domain.ArticleStore
import com.univent.api.article.tag.TagApi
import com.univent.api.article.tag.TagDto
import com.univent.api.common.core.domain.vo.identifier.ArticleId
import com.univent.api.common.core.domain.vo.identifier.TagId
import com.univent.api.common.exception.CustomException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UpdateArticleService(
    private val articleStore: ArticleStore,
    private val tagApi: TagApi
) : UpdateArticleUseCase {

    @Transactional
    override fun execute(command: UpdateArticleCommand) {
        // 1. 기존 Article 조회 (없으면 예외 발생)
        val article = articleStore.loadById(command.id)
            ?: throw CustomException(ArticleErrorCode.ARTICLE_NOT_FOUND)

        // 2. 태그 업데이트 처리 (전달된 경우에만 실행)
        command.tagNames?.let { names ->
            val tagIds = tagApi.findOrCreateTags(
                TagDto.FindOrCreateTagsRequest(tagNames = names.toMutableSet())
            ).tagIds

            // Article 도메인의 setTagIds 메서드 호출 (상태 변경)
            article.setTagIds(tagIds.map { TagId(it) })
        }

        // 3. 필드 업데이트 로직 수행
        article.update(
            title = command.title,
            organization = command.organization,
            description = command.description,
            location = command.location,
            startAt = command.startAt,
            endAt = command.endAt,
            registrationUrl = command.registrationUrl,
            registrationStartAt = command.registrationStartAt,
            registrationEndAt = command.registrationEndAt
        )

        // 4. 영속화 (Dirty Checking 혹은 명시적 save)
        articleStore.save(article)
    }
}
