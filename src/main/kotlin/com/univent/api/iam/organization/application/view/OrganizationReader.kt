package com.univent.api.iam.organization.application.view

import java.time.Instant

interface OrganizationReader {
    fun findById(id: Long): OrganizationView?
    fun findAllWithCursor(
        pageSize: Int,
        cursorId: Long?,
        cursorDate: Instant?
    ): List<OrganizationAdminView>
}
