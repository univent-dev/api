package com.univent.api.iam.organization.application.service

import com.univent.api.common.exception.CustomException
import com.univent.api.iam.organization.application.GetOrganizationUseCase
import com.univent.api.iam.organization.application.query.GetOrganizationQuery
import com.univent.api.iam.organization.application.view.OrganizationReader
import com.univent.api.iam.organization.application.view.OrganizationView
import com.univent.api.iam.organization.domain.OrganizationErrorCode
import org.springframework.stereotype.Service

@Service
class GetOrganizationService(
    private val organizationReader: OrganizationReader
): GetOrganizationUseCase {
    override fun execute(query: GetOrganizationQuery): OrganizationView {
        return organizationReader.findById(query.id) ?: throw CustomException(OrganizationErrorCode.ORGANIZATION_NOT_FOUND)
    }
}
