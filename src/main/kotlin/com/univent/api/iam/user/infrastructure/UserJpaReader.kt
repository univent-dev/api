package com.univent.api.iam.user.infrastructure

import com.univent.api.iam.user.infrastructure.projection.UserAdminProjection
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.Instant

interface UserJpaReader: JpaRepository<UserEntity, Long> {
    @Query("""
        SELECT
            u.id as id,
            u.email as email,
            u.createdAt as createdAt
        FROM UserEntity u
        WHERE (:cursorDate IS NULL OR :cursorId IS NULL) 
           OR (u.createdAt < :cursorDate OR (u.createdAt = :cursorDate AND u.id < :cursorId))
        ORDER BY u.createdAt DESC, u.id DESC
    """)
    fun findAllByCursor(
        pageable: Pageable,
        @Param("cursorId") cursorId: Long?,
        @Param("cursorDate") cursorDate: Instant?
    ): List<UserAdminProjection>
}
