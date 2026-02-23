package com.univent.api.iam.organization.application.command

data class CreateOrganizationCommand(
    val name: String,
    val contact: String
)
