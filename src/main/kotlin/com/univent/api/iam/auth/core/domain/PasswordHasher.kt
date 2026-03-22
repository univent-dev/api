package com.univent.api.iam.auth.core.domain

interface PasswordHasher {
    fun hash(plain: String): String
    fun compare(plain: String, hashed: String): Boolean
}
