package com.univent.api.article.tag.application.service

import com.univent.api.article.tag.application.FindOrCreateTagsUseCase
import com.univent.api.article.tag.application.command.FindOrCreateTagsCommand
import com.univent.api.article.tag.application.result.FindOrCreateTagsResult
import com.univent.api.article.tag.domain.Tag
import com.univent.api.article.tag.domain.TagStore
import com.univent.api.common.core.domain.IdGenerator
import com.univent.api.common.core.domain.vo.identifier.TagId
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class FindOrCreateTagsService(
    private val tagStore: TagStore,
    private val idGenerator: IdGenerator
) : FindOrCreateTagsUseCase {

    @Transactional
    override fun execute(command: FindOrCreateTagsCommand): FindOrCreateTagsResult {
        val tagIds = command.tagNames.map { tagName ->
            findOrCreateTag(tagName)
        }.map { it.id }.toSet()

        return FindOrCreateTagsResult(tagIds = tagIds)
    }

    private fun findOrCreateTag(name: String): Tag {
        return tagStore.loadByName(name) ?: createNewTag(name)
    }

    private fun createNewTag(name: String): Tag {
        val tag = Tag.create(
            id = TagId(idGenerator.generateId()),
            name = name
        )

        tagStore.save(tag)

        return tag
    }
}
