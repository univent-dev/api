package com.univent.api.iam.organization.application

import com.univent.api.iam.organization.application.query.GetOrganizationQuery
import com.univent.api.iam.organization.application.view.OrganizationView

interface GetOrganizationUseCase {
    fun execute(query: GetOrganizationQuery): OrganizationView
}
