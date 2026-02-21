package com.univent.api.iam.auth.user.presentation

import com.univent.api.common.core.presentation.UserPayload
import com.univent.api.iam.auth.user.application.AuthorizeOAuthUseCase
import com.univent.api.iam.auth.user.application.LogoutUseCase
import com.univent.api.iam.auth.user.application.OAuthLoginUseCase
import com.univent.api.iam.auth.user.application.RenewTokenUseCase
import com.univent.api.iam.auth.user.application.UnlinkOAuthUseCase
import com.univent.api.iam.auth.user.application.command.AuthorizeOAuthCommand
import com.univent.api.iam.auth.user.application.command.LogoutCommand
import com.univent.api.iam.auth.user.application.command.OAuthLoginCommand
import com.univent.api.iam.auth.user.application.command.RenewTokenCommand
import com.univent.api.iam.auth.user.application.command.UnlinkOAuthCommand
import com.univent.api.iam.auth.core.domain.OAuthProviderType
import com.univent.api.iam.user.domain.UserId
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseCookie
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthUserController(
    private val oAuthLoginUseCase: OAuthLoginUseCase,
    private val authorizeOAuthUseCase: AuthorizeOAuthUseCase,
    private val renewTokenUseCase: RenewTokenUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val unlinkOAuthUseCase: UnlinkOAuthUseCase
) {

    @GetMapping("/oauth/authorization")
    fun authorizeOAuth(@RequestParam(required = false) returnPath: String?): ResponseEntity<Any> {
        val command = AuthorizeOAuthCommand(
            oAuthProviderType = OAuthProviderType.KAKAO,
            redirectUrl = returnPath
        )

        val result = authorizeOAuthUseCase.execute(command)

        return ResponseEntity.ok(mapOf("authUrl" to result.authUrl, "returnPath" to returnPath))
    }

    @PostMapping("/login/oauth/callback")
    fun oAuthLogin(
        @RequestParam code: String,
        @RequestParam(required = false) error: String?,
        @RequestParam(required = false) state: String?
    ): ResponseEntity<Any> {
        if (error == "access_denied") {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(mapOf("message" to "Access denied"))
        }

        val command = OAuthLoginCommand(
            oAuthProviderType = OAuthProviderType.KAKAO,
            code = code,
            state = state
        )

        val result = oAuthLoginUseCase.execute(command)

        val accessCookie = createCookie("accessToken", result.accessToken, 10800)
        val refreshCookie = createCookie("refreshToken", result.refreshToken, 2592000) // 30일

        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, accessCookie.toString())
            .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
            .body(mapOf("userId" to result.userId, "redirectUrl" to result.redirectUrl))
    }

    @GetMapping("/refresh")
    @PreAuthorize("hasRole('USER')") // RolesGuard 대체
    fun renewToken(
        @AuthenticationPrincipal user: UserPayload,
        @CookieValue(name = "refreshToken") refreshToken: String
    ): ResponseEntity<Unit> {
        val command = RenewTokenCommand(
            userId = UserId(user.userId),
            refreshToken = refreshToken
        )

        val result = renewTokenUseCase.execute(command)

        val accessCookie = createCookie("accessToken", result.accessToken, 10800)
        val refreshCookie = createCookie("refreshToken", result.refreshToken, 2592000)

        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, accessCookie.toString())
            .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
            .build()
    }

    @PostMapping("/logout")
    @PreAuthorize("hasRole('USER')")
    fun logout(@AuthenticationPrincipal user: UserPayload): ResponseEntity<Unit> {
        val command = LogoutCommand(userId = UserId(user.userId))
        logoutUseCase.execute(command)

        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, expireCookie("accessToken").toString())
            .header(HttpHeaders.SET_COOKIE, expireCookie("refreshToken").toString())
            .build()
    }

    @PostMapping("/withdraw")
    @PreAuthorize("hasRole('USER')")
    fun withdraw(@AuthenticationPrincipal user: UserPayload): ResponseEntity<Unit> {
        val command = UnlinkOAuthCommand(
            userId = UserId(user.userId),
            provider = OAuthProviderType.KAKAO
        )
        unlinkOAuthUseCase.execute(command)

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
            .header(HttpHeaders.SET_COOKIE, expireCookie("accessToken").toString())
            .header(HttpHeaders.SET_COOKIE, expireCookie("refreshToken").toString())
            .build()
    }

    // --- Helper Methods ---

    private fun createCookie(name: String, value: String, maxAgeSeconds: Long): ResponseCookie {
        return ResponseCookie.from(name, value)
            .httpOnly(true)
            .secure(true)
            .path("/")
            .maxAge(maxAgeSeconds)
            .sameSite("None")
            .build()
    }

    private fun expireCookie(name: String): ResponseCookie {
        return ResponseCookie.from(name, "")
            .httpOnly(true)
            .secure(true)
            .path("/")
            .maxAge(0) // 즉시 만료
            .build()
    }
}
