package com.univent.api.iam.user.infrastructure.projection

import com.univent.api.iam.user.application.view.UserAdminView
import java.time.Instant

interface UserAdminProjection {
    val id: Long
    val email: String
    val createdAt: Instant

    fun toView() = UserAdminView(
        id = id.toString(),
        email = email,
        createdAt = createdAt
    )
}
