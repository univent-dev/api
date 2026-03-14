package com.univent.api.media.infrastructure

import org.springframework.data.jpa.repository.JpaRepository

interface MediaJpaStore : JpaRepository<MediaEntity, Long> {
    fun findAllByArticleId(articleId: Long): List<MediaEntity>
    fun deleteAllByIdIn(ids: List<Long>)
}
