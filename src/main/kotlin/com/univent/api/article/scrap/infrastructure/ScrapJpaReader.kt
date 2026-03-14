package com.univent.api.article.scrap.infrastructure

import org.springframework.data.jpa.repository.JpaRepository

interface ScrapJpaReader: JpaRepository<ScrapEntity, Long> {
    fun existsByArticleIdAndUserId(articleId: Long, userId: Long): Boolean
    fun findAllByUserId(userId: Long): List<ScrapEntity>
}
