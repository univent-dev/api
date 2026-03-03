package com.univent.api.article.tag.infrastructure

import com.univent.api.article.tag.domain.Tag
import com.univent.api.article.tag.domain.TagStore
import com.univent.api.common.core.domain.vo.identifier.TagId
import org.springframework.stereotype.Repository

@Repository
class TagStoreImpl(
    private val tagJpaStore: TagJpaStore
): TagStore {
    override fun save(tag: Tag) {
        val entity = TagEntity.fromDomain(tag)
        tagJpaStore.save(entity)
    }

    override fun loadById(id: TagId): Tag? {
        return tagJpaStore.findById(id.value).orElse(null)?.toDomain()
    }

    override fun loadByName(name: String): Tag? {
        return tagJpaStore.findByName(name)?.toDomain()
    }
}
