package com.univent.api.iam.organization.infrastructure.projection

import com.univent.api.iam.organization.application.view.OrganizationAdminView
import java.time.Instant

interface OrganizationAdminProjection {
    val id: Long
    val name: String
    val contact: String
    val createdAt: Instant

    fun toView() = OrganizationAdminView(
        id = id.toString(),
        name = name,
        contact = contact,
        createdAt = createdAt
    )
}
