package com.univent.api.iam.organization.application

import com.univent.api.iam.organization.application.query.GetAllOrganizationsQuery
import com.univent.api.iam.organization.application.view.OrganizationAdminView

interface GetAllOrganizationsUseCase {
    fun execute(query: GetAllOrganizationsQuery): List<OrganizationAdminView>
}
