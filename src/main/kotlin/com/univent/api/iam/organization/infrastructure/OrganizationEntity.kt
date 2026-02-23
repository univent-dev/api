package com.univent.api.iam.organization.infrastructure

import com.univent.api.iam.organization.domain.Organization
import com.univent.api.iam.organization.domain.OrganizationId
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.Instant

@Entity
@Table(name = "organization")
class OrganizationEntity(
    @Id
    val id: Long,

    @Column(nullable = false, length = 64)
    val name: String,

    @Column(nullable = false, length = 20)
    val contact: String,

    @Column(nullable = false, updatable = false)
    val createdAt: Instant,

    @Column(nullable = false)
    val updatedAt: Instant
) {
    companion object {
        fun fromDomain(organization: Organization): OrganizationEntity {
            return OrganizationEntity(
                id = organization.id.value,
                name = organization.name,
                contact = organization.contact,
                createdAt = organization.createdAt,
                updatedAt = organization.updatedAt
            )
        }
    }

    fun toDomain(): Organization {
        return Organization.of(
            id = OrganizationId(id),
            name = name,
            contact = contact,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }
}
