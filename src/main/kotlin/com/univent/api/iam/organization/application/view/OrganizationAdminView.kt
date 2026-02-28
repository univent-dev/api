package com.univent.api.iam.organization.application.view

import java.time.Instant

data class OrganizationAdminView(
    val id: String,
    val name: String,
    val contact: String,
    val createdAt: Instant
)
