package com.univent.api.iam.user.application.view

import java.time.Instant

data class UserAdminView (
    val id: String,
    val email: String,
    val createdAt: Instant
)
