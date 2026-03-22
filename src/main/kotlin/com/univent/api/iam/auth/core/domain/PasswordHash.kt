package com.univent.api.iam.auth.core.domain

class PasswordHash(val value: String) {
    companion object {
        fun create(hash: String): PasswordHash {
            return PasswordHash(hash)
        }
    }
}
