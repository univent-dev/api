package com.univent.api.article.tag.infrastructure

import com.univent.api.article.tag.domain.Tag
import com.univent.api.common.core.domain.vo.identifier.TagId
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.Instant

@Entity
@Table(name = "tag")
class TagEntity(
    @Id
    val id: Long,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false, updatable = false)
    val createdAt: Instant,

    @Column(nullable = false)
    val updatedAt: Instant
) {
    companion object {
        fun fromDomain(tag: Tag): TagEntity {
            return TagEntity(
                id = tag.id.value,
                name = tag.name,
                createdAt = tag.createdAt,
                updatedAt = tag.updatedAt
            )
        }
    }

    fun toDomain(): Tag {
        return Tag.of(
            id = TagId(id),
            name = name,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }
}
