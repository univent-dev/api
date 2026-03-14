package com.univent.api.article.scrap.infrastructure

import org.springframework.data.jpa.repository.JpaRepository

interface ScrapJpaStore: JpaRepository<ScrapEntity, Long> {
    fun findByArticleIdAndUserId(articleId: Long, userId: Long): ScrapEntity?
    fun deleteByArticleIdAndUserId(articleId: Long, userId: Long)
}
