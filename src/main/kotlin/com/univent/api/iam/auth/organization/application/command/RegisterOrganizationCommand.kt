package com.univent.api.iam.auth.organization.application.command

data class RegisterOrganizationCommand(
    val accountId: String,
    val password: String,
    val name: String,
    val contact: String
)
