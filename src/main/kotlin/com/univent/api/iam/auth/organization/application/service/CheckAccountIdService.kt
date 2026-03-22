package com.univent.api.iam.auth.organization.application.service

import com.univent.api.iam.auth.organization.application.CheckAccountIdUseCase
import com.univent.api.iam.auth.organization.application.command.CheckAccountIdCommand
import com.univent.api.iam.auth.organization.domain.AuthOrganizationStore
import com.univent.api.iam.auth.organization.domain.vo.AccountId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CheckAccountIdService(
    private val authOrganizationStore: AuthOrganizationStore
) : CheckAccountIdUseCase {
    @Transactional(readOnly = true)
    override fun execute(command: CheckAccountIdCommand): Boolean {
        val accountId = AccountId.create(command.accountId.trim())

        return authOrganizationStore.existsByAccountId(accountId)
    }
}
