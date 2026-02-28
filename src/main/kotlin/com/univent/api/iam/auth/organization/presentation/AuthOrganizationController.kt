package com.univent.api.iam.auth.organization.presentation

import com.univent.api.common.core.presentation.OrganizationPayload
import com.univent.api.iam.auth.organization.application.ChangeOrganizationPasswordUseCase
import com.univent.api.iam.auth.organization.application.CheckAccountIdUseCase
import com.univent.api.iam.auth.organization.application.OrganizationLoginUseCase
import com.univent.api.iam.auth.organization.application.OrganizationLogoutUseCase
import com.univent.api.iam.auth.organization.application.RegisterOrganizationUseCase
import com.univent.api.iam.auth.organization.application.RenewOrganizationTokenUseCase
import com.univent.api.iam.auth.organization.application.WithdrawOrganizationUseCase
import com.univent.api.iam.auth.organization.application.command.ChangeOrganizationPasswordCommand
import com.univent.api.iam.auth.organization.application.command.CheckAccountIdCommand
import com.univent.api.iam.auth.organization.application.command.OrganizationLoginCommand
import com.univent.api.iam.auth.organization.application.command.OrganizationLogoutCommand
import com.univent.api.iam.auth.organization.application.command.RegisterOrganizationCommand
import com.univent.api.iam.auth.organization.application.command.RenewOrganizationTokenCommand
import com.univent.api.iam.auth.organization.application.command.WithdrawOrganizationCommand
import com.univent.api.iam.auth.organization.presentation.dto.ChangeOrganizationPasswordDto
import com.univent.api.iam.auth.organization.presentation.dto.CheckAccountIdDto
import com.univent.api.iam.auth.organization.presentation.dto.OrganizationLoginDto
import com.univent.api.iam.auth.organization.presentation.dto.RegisterOrganizationDto
import com.univent.api.iam.organization.domain.OrganizationId
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth/organization")
class AuthOrganizationController(
    private val registerOrganizationUseCase: RegisterOrganizationUseCase,
    private val renewTokenUseCase: RenewOrganizationTokenUseCase,
    private val organizationLoginUseCase: OrganizationLoginUseCase,
    private val organizationLogoutUseCase: OrganizationLogoutUseCase,
    private val checkAccountIdUseCase: CheckAccountIdUseCase,
    private val withdrawOrganizationUseCase: WithdrawOrganizationUseCase,
    private val changeOrganizationPasswordUseCase: ChangeOrganizationPasswordUseCase
) {

    @PostMapping("/register")
    fun register(@RequestBody dto: RegisterOrganizationDto.Req): ResponseEntity<Unit> {
        val command = RegisterOrganizationCommand(
            accountId = dto.accountId,
            password = dto.password,
            name = dto.name,
            contact = dto.contact
        )

        registerOrganizationUseCase.execute(command)

        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @PostMapping("/check-account-id")
    fun checkAccountId(@RequestBody dto: CheckAccountIdDto.Req): CheckAccountIdDto.Res {
        val isDuplicated = checkAccountIdUseCase.execute(CheckAccountIdCommand(dto.accountId))
        return CheckAccountIdDto.Res(isDuplicated = isDuplicated)
    }

    @PostMapping("/login")
    fun login(
        @RequestBody dto: OrganizationLoginDto.Req,
        response: HttpServletResponse
    ): ResponseEntity<Unit> {
        val command = OrganizationLoginCommand(
            accountId = dto.accountId,
            password = dto.password
        )
        val result = organizationLoginUseCase.execute(command)

        setAuthCookies(response, result.accessToken, result.refreshToken)

        return ResponseEntity.ok().build()
    }

    @PostMapping("/refresh")
    fun renewToken(
        @AuthenticationPrincipal organization: OrganizationPayload,
        @CookieValue("organizationRefreshToken") refreshToken: String,
        response: HttpServletResponse
    ): ResponseEntity<Unit> {
        val command = RenewOrganizationTokenCommand(
            organizationId = OrganizationId(organization.id),
            refreshToken = refreshToken
        )

        val result = renewTokenUseCase.execute(command)

        setAuthCookies(response, result.accessToken, result.refreshToken)

        return ResponseEntity.ok().build()
    }

    @PostMapping("/logout")
    fun logout(
        @AuthenticationPrincipal organization: OrganizationPayload,
        response: HttpServletResponse
    ): ResponseEntity<Unit> {
        val command = OrganizationLogoutCommand(OrganizationId(organization.id))
        organizationLogoutUseCase.execute(command)

        clearAuthCookies(response)

        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/withdraw")
    fun withdraw(
        @AuthenticationPrincipal organization: OrganizationPayload,
        response: HttpServletResponse
    ): ResponseEntity<Unit> {
        val command = WithdrawOrganizationCommand(OrganizationId(organization.id))
        withdrawOrganizationUseCase.execute(command)

        clearAuthCookies(response)

        return ResponseEntity.ok().build()
    }

    @PatchMapping("/change-password")
    fun changePassword(
        @AuthenticationPrincipal organization: OrganizationPayload,
        @RequestBody dto: ChangeOrganizationPasswordDto.Req
    ): ResponseEntity<Unit> {
        changeOrganizationPasswordUseCase.execute(
            ChangeOrganizationPasswordCommand(
                organizationId = OrganizationId(organization.id),
                currentPassword = dto.currentPassword,
                newPassword = dto.newPassword
            )
        )
        return ResponseEntity.ok().build()
    }

    private fun setAuthCookies(response: HttpServletResponse, accessToken: String, refreshToken: String) {
        val accessCookie = Cookie("organizationAccessToken", accessToken).apply {
            isHttpOnly = true
            path = "/"
            maxAge = 3600 // 예: 1시간
        }
        val refreshCookie = Cookie("organizationRefreshToken", refreshToken).apply {
            isHttpOnly = true
            path = "/"
            maxAge = 604800 // 예: 7일
        }
        response.addCookie(accessCookie)
        response.addCookie(refreshCookie)
    }

    private fun clearAuthCookies(response: HttpServletResponse) {
        val accessCookie = Cookie("organizationAccessToken", null).apply {
            maxAge = 0
            path = "/"
        }
        val refreshCookie = Cookie("organizationRefreshToken", null).apply {
            maxAge = 0
            path = "/"
        }
        response.addCookie(accessCookie)
        response.addCookie(refreshCookie)
    }
}
