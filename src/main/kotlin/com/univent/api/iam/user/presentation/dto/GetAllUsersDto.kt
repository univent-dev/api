package com.univent.api.iam.user.presentation.dto

import java.time.Instant

sealed interface GetAllUsersDto {
    data class Req(
        val pageSize: Int = 10,
        val cursorId: Long? = null,
        val cursorDate: Instant? = null
    )
}
