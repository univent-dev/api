package com.univent.api.iam.auth.organization.application.service

import com.univent.api.common.exception.CustomException
import com.univent.api.iam.auth.core.domain.AuthErrorCode
import com.univent.api.iam.auth.core.domain.PasswordHash
import com.univent.api.iam.auth.core.domain.PasswordHasher
import com.univent.api.iam.auth.organization.application.ChangeOrganizationPasswordUseCase
import com.univent.api.iam.auth.organization.application.command.ChangeOrganizationPasswordCommand
import com.univent.api.iam.auth.organization.domain.AuthOrganizationStore
import com.univent.api.iam.auth.organization.domain.vo.RawPassword
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChangeOrganizationPasswordService(
    private val authOrganizationStore: AuthOrganizationStore,
    private val passwordHasher: PasswordHasher
) : ChangeOrganizationPasswordUseCase {
    @Transactional
    override fun execute(command: ChangeOrganizationPasswordCommand) {
        val (organizationId, currentPassword, newPassword) = command

        val authOrganization = authOrganizationStore.findByOrganizationId(organizationId)
            ?: throw CustomException(AuthErrorCode.AUTH_ORGANIZATION_NOT_FOUND)

        val isPasswordValid = passwordHasher.compare(
            plain = currentPassword.trim(),
            hashed = authOrganization.passwordHash.value
        )
        if (!isPasswordValid) {
            throw CustomException(AuthErrorCode.AUTH_INVALID_PASSWORD)
        }

        val rawNewPassword = RawPassword.create(newPassword.trim())

        val newPasswordHash = passwordHasher.hash(rawNewPassword.value)
        authOrganization.updatePassword(PasswordHash(newPasswordHash))

        authOrganizationStore.update(authOrganization)
    }
}
