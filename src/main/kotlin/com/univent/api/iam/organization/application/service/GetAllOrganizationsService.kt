package com.univent.api.iam.organization.application.service

import com.univent.api.iam.organization.application.GetAllOrganizationsUseCase
import com.univent.api.iam.organization.application.query.GetAllOrganizationsQuery
import com.univent.api.iam.organization.application.view.OrganizationAdminView
import com.univent.api.iam.organization.application.view.OrganizationReader
import org.springframework.stereotype.Service

@Service
class GetAllOrganizationsService(
    private val organizationReader: OrganizationReader
): GetAllOrganizationsUseCase {
    override fun execute(query: GetAllOrganizationsQuery): List<OrganizationAdminView> {
        return organizationReader.findAllWithCursor(
            pageSize = query.pageSize,
            cursorId = query.cursorId,
            cursorDate = query.cursorDate
        )
    }
}
