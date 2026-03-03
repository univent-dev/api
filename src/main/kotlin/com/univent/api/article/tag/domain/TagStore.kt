package com.univent.api.article.tag.domain

import com.univent.api.common.core.domain.vo.identifier.TagId

interface TagStore {
    fun save(tag: Tag)
    fun loadById(id: TagId): Tag?
    fun loadByName(name: String): Tag?
}
