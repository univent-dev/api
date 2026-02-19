package com.univent.api.iam.user.application.query

import java.time.Instant

data class GetAllUsersQuery(
    val pageSize: Int = 10,
    val cursorId: Long? = null,
    val cursorDate: Instant? = null
)
