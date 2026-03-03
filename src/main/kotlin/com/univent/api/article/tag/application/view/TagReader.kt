package com.univent.api.article.tag.application.view

interface TagReader {
    fun findAllByIds(ids: Set<Long>): List<TagView>
    fun findByName(name: String): TagView
    fun findAll(): List<TagView>
}
