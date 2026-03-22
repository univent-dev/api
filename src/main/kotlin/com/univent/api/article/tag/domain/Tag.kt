package com.univent.api.article.tag.domain

import com.univent.api.common.core.domain.AggregateRoot
import com.univent.api.common.core.domain.vo.identifier.TagId
import com.univent.api.common.exception.CustomException
import java.time.Instant

class Tag private constructor(
    id: TagId,
    name: String,
    val createdAt: Instant = Instant.now(),
    updatedAt: Instant = Instant.now()
) : AggregateRoot<TagId>(id) {
    var name: String = name; private set
    var updatedAt: Instant = updatedAt; private set

    init { validate() }

    companion object {
        fun create(
            id: TagId,
            name: String
        ): Tag {
            return Tag(
                id = id,
                name = name.trim()
            )
        }

        fun of(
            id: TagId,
            name: String,
            createdAt: Instant,
            updatedAt: Instant
        ): Tag {
            return Tag(
                id = id,
                name = name,
                createdAt = createdAt,
                updatedAt = updatedAt
            )
        }
    }

    private fun validate() {
        require(name.isNotBlank()) { throw CustomException(TagErrorCode.TAG_NAME_EMPTY) }
        require(name.length <= 20) { throw CustomException(TagErrorCode.TAG_NAME_TOO_LONG) }
    }

    fun updateName(newName: String) {
        this.name = newName.trim()
        this.updatedAt = Instant.now()
        validate()
    }
}
