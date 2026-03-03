package com.univent.api.article.article.infrastructure

import org.springframework.data.jpa.repository.JpaRepository

interface ArticleJpaReader: JpaRepository<ArticleEntity, Long> {
}
