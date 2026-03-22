package com.univent.api.article.scrap.application.view

interface ScrapReader {
    fun existsByArticleIdAndUserId(articleId: Long, userId: Long): Boolean
    fun findArticleIdsByUserId(userId: Long): List<Long>
}
