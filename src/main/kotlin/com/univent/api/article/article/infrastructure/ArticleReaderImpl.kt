package com.univent.api.article.article.infrastructure

import com.univent.api.article.article.application.view.ArticleReadModel
import com.univent.api.article.article.application.view.ArticleReader
import com.univent.api.article.article.application.view.SearchType
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository
import java.time.Instant

@Repository
class ArticleReaderImpl(
    private val articleJpaReader: ArticleJpaReader,
    private val em: EntityManager
): ArticleReader {
    override fun findById(id: Long): ArticleReadModel? {
        val entity = articleJpaReader.findById(id)
        return entity.map {
            ArticleReadModel(
                id = it.id,
                title = it.title,
                organization = it.organization,
                scrapCount = it.scrapCount,
                viewCount = it.viewCount,
                startAt = it.startAt,
                endAt = it.endAt,
                registrationStartAt = it.registrationStartAt,
                registrationEndAt = it.registrationEndAt,
                description = it.description,
                location = it.location,
                registrationUrl = it.registrationUrl,
                tagIds = it.tagIds,
                thumbnailId = 0L,
                imageIds = emptyList()
            )
        }.orElse(null)
    }

    override fun findAllByCriteria(
        tags: List<String>?, // 태그 이름 리스트
        isFinished: Boolean?,
        sortBy: String?,
        keyword: String?,
        searchType: SearchType?
    ): List<ArticleReadModel> {
        val now = Instant.now()

        // 1. SELECT a (Entity 자체를 조회)
        val jpql = StringBuilder("SELECT DISTINCT a FROM ArticleEntity a")

        // 조인 조건 (태그 필터링 시)
        if (!tags.isNullOrEmpty()) {
            jpql.append(" JOIN a.tagIds tid JOIN TagEntity t ON tid = t.id")
        }

        // 2. WHERE 절 동적 구성
        val whereClauses = mutableListOf<String>()
        val params = mutableMapOf<String, Any>()

        if (!tags.isNullOrEmpty()) {
            whereClauses.add(" t.name IN :tags ")
            params["tags"] = tags
        }

        if (isFinished == false) {
            whereClauses.add(" a.endAt >= :now ")
            params["now"] = now
        }

        if (!keyword.isNullOrBlank()) {
            val searchClause = when (searchType) {
                SearchType.TITLE -> " a.title LIKE :keyword "
                SearchType.ORGANIZATION -> " a.organization LIKE :keyword "
                else -> " (a.title LIKE :keyword OR a.organization LIKE :keyword) "
            }
            whereClauses.add(searchClause)
            params["keyword"] = "%${keyword}%"
        }

        if (whereClauses.isNotEmpty()) {
            jpql.append(" WHERE ").append(whereClauses.joinToString(" AND "))
        }

        // 3. ORDER BY 절 (정렬 로직)
        val orderSection = if (!keyword.isNullOrBlank() && sortBy == null) {
            " ORDER BY (CASE WHEN COALESCE(a.registrationStartAt, a.startAt) >= :now THEN 0 ELSE 1 END) ASC, " +
                    " ABS(TIMESTAMPDIFF(SECOND, COALESCE(a.registrationStartAt, a.startAt), :now)) ASC "
        } else {
            when (sortBy) {
                "scrapCount" -> " ORDER BY a.scrapCount DESC "
                "viewCount" -> " ORDER BY a.viewCount DESC "
                "createdAt" -> " ORDER BY a.createdAt DESC "
                else -> " ORDER BY a.id DESC "
            }
        }
        jpql.append(orderSection)

        // 4. 실행 및 DTO 변환
        val typedQuery = em.createQuery(jpql.toString(), ArticleEntity::class.java)
        params.forEach { (k, v) -> typedQuery.setParameter(k, v) }

        // 정렬이나 필터에서 :now를 사용하는 경우 바인딩
        if (jpql.contains(":now")) typedQuery.setParameter("now", now)

        return typedQuery.resultList.map { it.toReadModel() }
    }

    // 변환 로직을 별도 확장 함수나 메서드로 분리 (중복 제거)
    private fun ArticleEntity.toReadModel() = ArticleReadModel(
        id = this.id,
        title = this.title,
        organization = this.organization,
        scrapCount = this.scrapCount,
        viewCount = this.viewCount,
        startAt = this.startAt,
        endAt = this.endAt,
        registrationStartAt = this.registrationStartAt,
        registrationEndAt = this.registrationEndAt,
        description = this.description,
        location = this.location,
        registrationUrl = this.registrationUrl,
        tagIds = this.tagIds,
        thumbnailId = 0L, // 미디어 도메인 연동 전 기본값
        imageIds = emptyList()
    )
}
