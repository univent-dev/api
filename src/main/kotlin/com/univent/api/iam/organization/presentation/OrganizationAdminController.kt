package com.univent.api.iam.organization.presentation

import com.univent.api.iam.organization.application.GetAllOrganizationsUseCase
import com.univent.api.iam.organization.application.query.GetAllOrganizationsQuery
import com.univent.api.iam.organization.application.view.OrganizationAdminView
import com.univent.api.iam.organization.presentation.dto.GetAllOrganizationsDto
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/organization/admin")
class OrganizationAdminController(
    private val getAllOrganizationsUseCase: GetAllOrganizationsUseCase
) {
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    fun getAll(
        @ModelAttribute req: GetAllOrganizationsDto.Req
    ): List<OrganizationAdminView> {
        val query = GetAllOrganizationsQuery(
            pageSize = req.pageSize,
            cursorId = req.cursorId,
            cursorDate = req.cursorDate
        )

        return getAllOrganizationsUseCase.execute(query)
    }
}
