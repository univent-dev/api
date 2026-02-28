package com.univent.api.iam.organization.presentation

import com.univent.api.common.core.presentation.OrganizationPayload
import com.univent.api.iam.organization.application.GetOrganizationUseCase
import com.univent.api.iam.organization.application.UpdateOrganizationUseCase
import com.univent.api.iam.organization.application.command.UpdateOrganizationCommand
import com.univent.api.iam.organization.application.query.GetOrganizationQuery
import com.univent.api.iam.organization.application.view.OrganizationView
import com.univent.api.iam.organization.domain.OrganizationId
import com.univent.api.iam.organization.presentation.dto.UpdateOrganizationDto
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/organization")
class OrganizationController(
    private val updateOrganizationUseCase: UpdateOrganizationUseCase,
    private val getOrganizationUseCase: GetOrganizationUseCase
) {
    @PatchMapping
    @PreAuthorize("hasRole('ORGANIZATION')")
    fun updateOrganization(
        @AuthenticationPrincipal organization: OrganizationPayload,
        @RequestBody dto: UpdateOrganizationDto.Req
    ) {
        val command = UpdateOrganizationCommand(
            organizationId = OrganizationId(organization.id),
            name = dto.name,
            contact = dto.contact
        )

        updateOrganizationUseCase.execute(command)
    }

    @GetMapping
    @PreAuthorize("hasRole('ORGANIZATION')")
    fun getOrganization(
        @AuthenticationPrincipal organization: OrganizationPayload
    ): OrganizationView {
        val query = GetOrganizationQuery(organization.id)

        return getOrganizationUseCase.execute(query)
    }
}
