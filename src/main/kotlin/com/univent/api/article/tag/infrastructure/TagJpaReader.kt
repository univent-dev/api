package com.univent.api.article.tag.infrastructure

import org.springframework.data.jpa.repository.JpaRepository

interface TagJpaReader: JpaRepository<TagEntity, Long> {

}
