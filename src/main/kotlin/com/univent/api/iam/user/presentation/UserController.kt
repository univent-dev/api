package com.univent.api.iam.user.presentation

import com.univent.api.common.core.presentation.UserPayload
import com.univent.api.iam.user.application.GetAllUsersUseCase
import com.univent.api.iam.user.application.GetUserUseCase
import com.univent.api.iam.user.application.query.GetAllUsersQuery
import com.univent.api.iam.user.application.query.GetUserQuery
import com.univent.api.iam.user.application.view.UserAdminView
import com.univent.api.iam.user.application.view.UserView
import com.univent.api.iam.user.presentation.dto.GetAllUsersDto
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController(
    private val getUserUseCase: GetUserUseCase,
    private val getAllUsersUseCase: GetAllUsersUseCase
) {
    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    fun getMyInfo(
        @AuthenticationPrincipal user: UserPayload
    ): UserView {
        val query = GetUserQuery(user.userId)
        return getUserUseCase.execute(query)
    }

    @GetMapping("/admin")
    //@PreAuthorize("hasRole('ADMIN')")
    fun getAll(@ModelAttribute dto: GetAllUsersDto.Req): List<UserAdminView> {
        val query = GetAllUsersQuery(
            pageSize = dto.pageSize,
            cursorId = dto.cursorId,
            cursorDate = dto.cursorDate
        )
        return getAllUsersUseCase.execute(query)
    }
}
