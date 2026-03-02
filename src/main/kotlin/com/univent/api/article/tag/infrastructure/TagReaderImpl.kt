package com.univent.api.article.tag.infrastructure

import com.univent.api.article.tag.application.view.TagReader
import com.univent.api.article.tag.application.view.TagView
import org.springframework.stereotype.Repository

@Repository
class TagReaderImpl(
    private val tagJpaReader: TagJpaReader
): TagReader {
    override fun findAllByIds(ids: Set<Long>): List<TagView> {
        if (ids.isEmpty()) return emptyList()
        val entities = tagJpaReader.findAllById(ids)

        return entities.map {
            TagView(
                id = it.id,
                name = it.name
            )
        }
    }

    override fun findByName(name: String): TagView {
        TODO("Not yet implemented")
    }

    override fun findAll(): List<TagView> {
        TODO("Not yet implemented")
    }

}
