package com.univent.api.iam.auth.core.infrastructure

import com.univent.api.common.exception.CustomException
import com.univent.api.iam.auth.core.domain.AuthErrorCode
import com.univent.api.iam.auth.core.domain.PasswordHasher
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class PasswordHasherImpl(
    private val passwordEncoder: PasswordEncoder
) : PasswordHasher {
    override fun hash(plain: String): String {
        return passwordEncoder.encode(plain) ?: throw CustomException(AuthErrorCode.AUTH_PASSWORD_ENCODING_FAILED)
    }

    override fun compare(plain: String, hashed: String): Boolean {
        return passwordEncoder.matches(plain, hashed)
    }
}
