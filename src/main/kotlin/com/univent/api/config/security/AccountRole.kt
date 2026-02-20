package com.univent.api.config.security

enum class AccountRole(val value: String) {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN"),
    ORGANIZATION("ROLE_ORGANIZATION")
}
