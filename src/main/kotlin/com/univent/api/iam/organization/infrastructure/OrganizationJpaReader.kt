package com.univent.api.iam.organization.infrastructure

import com.univent.api.iam.organization.infrastructure.projection.OrganizationAdminProjection
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.Instant

interface OrganizationJpaReader: JpaRepository<OrganizationEntity, Long> {
    @Query("""
        SELECT 
            o.id as id, 
            o.name as name, 
            o.contact as contact, 
            o.createdAt as createdAt 
        FROM OrganizationEntity o 
        WHERE (:cursorDate IS NULL AND :cursorId IS NULL) 
           OR (o.createdAt < :cursorDate 
            OR (o.createdAt = :cursorDate AND o.id < :cursorId))
        ORDER BY o.createdAt DESC, o.id DESC
    """)
    fun findAllByCursor(
        pageable: Pageable,
        @Param("cursorId") cursorId: Long?,
        @Param("cursorDate") cursorDate: Instant?
    ): List<OrganizationAdminProjection>
}
