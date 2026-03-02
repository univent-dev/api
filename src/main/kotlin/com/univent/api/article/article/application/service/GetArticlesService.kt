package com.univent.api.article.article.application.service

import com.univent.api.article.article.application.GetArticlesUseCase
import com.univent.api.article.article.application.query.GetArticlesQuery
import com.univent.api.article.article.application.view.ArticleReader
import com.univent.api.article.article.application.view.ArticleView
import com.univent.api.article.tag.application.view.TagReader
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GetArticlesService(
    private val articleReader: ArticleReader,
    private val tagReader: TagReader
) : GetArticlesUseCase {
    @Transactional(readOnly = true)
    override fun execute(query: GetArticlesQuery): List<ArticleView> {
        // 1. Article 및 기본 정보 조회 (Thumbnail 포함, 1번의 쿼리)
        // 이 단계에서 articleReader는 DB의 CASE WHEN 정렬과 필터링을 수행합니다.
        val readModels = articleReader.findAllByCriteria(
            tags = query.tags,
            isFinished = query.isFinished,
            sortBy = query.sortBy,
            keyword = query.keyword,
            searchType = query.searchType
        )

        if (readModels.isEmpty()) return emptyList()

        // 2. 모든 게시글에서 사용된 중복 없는 Tag ID 수집
        val allTagIds = readModels.flatMap { it.tagIds }.toSet()

        // 3. Tag 정보 조회 및 Map 변환 (핵심!)
        val tagViews = tagReader.findAllByIds(allTagIds)

        // ID를 Key로, 이름을 Value로 하는 Map 생성 (O(1) 조회를 위해)
        val tagMap: Map<Long, String> = tagViews.associate { it.id to it.name }

        // 4. 최종 조립
        return readModels.map { model ->
            model.toArticleView(
                tags = model.tagIds.mapNotNull { tagId -> tagMap[tagId] },
                thumbnailPath = ""
            )
        }
    }
}
