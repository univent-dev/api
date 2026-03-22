package com.univent.api.iam.organization.application

import com.univent.api.iam.organization.application.command.CreateOrganizationCommand
import com.univent.api.iam.organization.application.result.CreateOrganizationResult

interface CreateOrganizationUseCase {
    fun execute(command: CreateOrganizationCommand): CreateOrganizationResult
}
