package com.univent.api.iam.auth.organization.application.service

import com.univent.api.common.core.domain.vo.AccountRole
import com.univent.api.common.exception.CustomException
import com.univent.api.iam.JwtApi
import com.univent.api.iam.auth.core.domain.AuthErrorCode
import com.univent.api.iam.auth.core.domain.PasswordHasher
import com.univent.api.iam.auth.organization.application.OrganizationLoginUseCase
import com.univent.api.iam.auth.organization.application.command.OrganizationLoginCommand
import com.univent.api.iam.auth.organization.application.result.LoginResult
import com.univent.api.iam.auth.organization.domain.AuthOrganization
import com.univent.api.iam.auth.organization.domain.AuthOrganizationStore
import com.univent.api.iam.auth.organization.domain.vo.AccountId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrganizationOrganizationLoginService(
    private val authOrganizationStore: AuthOrganizationStore,
    private val passwordHasher: PasswordHasher,
    private val jwtApi: JwtApi
) : OrganizationLoginUseCase {

    @Transactional
    override fun execute(command: OrganizationLoginCommand): LoginResult {
        val authOrganization = validateAccount(
            accountId = command.accountId.trim(),
            password = command.password.trim()
        )

        val tokens = generateTokens(authOrganization.organizationId.value)

        saveRefreshToken(authOrganization, tokens.refreshToken)

        return LoginResult(
            accessToken = tokens.accessToken,
            refreshToken = tokens.refreshToken
        )
    }

    private fun validateAccount(accountId: String, password: String): AuthOrganization {
        val validAccountId = AccountId.create(accountId)

        val authOrganization = authOrganizationStore.findByAccountId(validAccountId)
            ?: throw CustomException(AuthErrorCode.AUTH_ORGANIZATION_NOT_FOUND)

        val isPasswordValid = passwordHasher.compare(password, authOrganization.passwordHash.value)
        if (!isPasswordValid) {
            throw CustomException(AuthErrorCode.AUTH_INVALID_PASSWORD)
        }

        return authOrganization
    }

    private fun generateTokens(organizationId: Long): TokenGenerationResult {
        val accessToken = jwtApi.createAccessToken(
            sub = organizationId,
            roles = listOf(AccountRole.ORGANIZATION)
        )

        val refreshToken = jwtApi.createRefreshToken(
            sub = organizationId,
            roles = listOf(AccountRole.ORGANIZATION)
        )

        return TokenGenerationResult(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    private fun saveRefreshToken(authOrganization: AuthOrganization, refreshToken: String) {
        authOrganization.updateRefreshToken(refreshToken)
        authOrganizationStore.update(authOrganization)
    }

    private data class TokenGenerationResult(
        val accessToken: String,
        val refreshToken: String
    )
}
