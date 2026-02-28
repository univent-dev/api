package com.univent.api.iam.auth.organization.application.command

data class OrganizationLoginCommand(
    val accountId: String,
    val password: String
)
