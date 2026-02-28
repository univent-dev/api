package com.univent.api.iam.auth.organization.application.service

import com.univent.api.common.core.domain.IdGenerator
import com.univent.api.common.exception.CustomException
import com.univent.api.iam.auth.core.domain.AuthErrorCode
import com.univent.api.iam.auth.core.domain.PasswordHash
import com.univent.api.iam.auth.core.domain.PasswordHasher
import com.univent.api.iam.auth.organization.application.RegisterOrganizationUseCase
import com.univent.api.iam.auth.organization.application.command.RegisterOrganizationCommand
import com.univent.api.iam.auth.organization.domain.AuthOrganization
import com.univent.api.iam.auth.organization.domain.AuthOrganizationId
import com.univent.api.iam.auth.organization.domain.AuthOrganizationStore
import com.univent.api.iam.auth.organization.domain.vo.AccountId
import com.univent.api.iam.auth.organization.domain.vo.RawPassword
import com.univent.api.iam.organization.OrganizationApi
import com.univent.api.iam.organization.OrganizationDto
import com.univent.api.iam.organization.domain.OrganizationId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RegisterOrganizationService(
    private val authOrganizationStore: AuthOrganizationStore,
    private val passwordHasher: PasswordHasher,
    private val organizationApi: OrganizationApi,
    private val idGenerator: IdGenerator
) : RegisterOrganizationUseCase {

    @Transactional
    override fun execute(command: RegisterOrganizationCommand) {
        val accountId = AccountId.create(command.accountId.trim())
        if (authOrganizationStore.existsByAccountId(accountId)) {
            throw CustomException(AuthErrorCode.AUTH_ORGANIZATION_ACCOUNT_ID_ALREADY_EXISTS)
        }

        val organizationId = createOrganization(command.name, command.contact)

        createAuthOrganization(
            accountId = accountId,
            password = command.password,
            organizationId = organizationId
        )
    }

    private fun createOrganization(name: String, contact: String): OrganizationId {
        val response = organizationApi.createOrganization(
            OrganizationDto.CreateOrganizationRequest(name = name, contact = contact)
        )
        return OrganizationId(response.id)
    }

    private fun createAuthOrganization(
        accountId: AccountId,
        password: String,
        organizationId: OrganizationId
    ): AuthOrganization {
        val rawPassword = RawPassword.create(password.trim())

        val hashedPassword = passwordHasher.hash(rawPassword.value)

        val authOrganization = AuthOrganization.create(
            id = AuthOrganizationId(idGenerator.generateId()),
            accountId = accountId,
            passwordHash = PasswordHash(hashedPassword),
            organizationId = organizationId
        )

        authOrganizationStore.save(authOrganization)

        return authOrganization
    }
}
