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
        val entity = tagJpaReader.findByName(name)
            ?: throw IllegalArgumentException("'$name' 태그가 존재하지 않습니다.")

        return TagView(
            id = entity.id,
            name = entity.name
        )
    }

    override fun findAll(): List<TagView> {
        val entities = tagJpaReader.findAll()

        return entities.map {
            TagView(
                id = it.id,
                name = it.name
            )
        }
    }
}
