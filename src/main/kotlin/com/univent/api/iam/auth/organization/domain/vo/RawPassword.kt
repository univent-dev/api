package com.univent.api.iam.auth.organization.domain.vo

import com.univent.api.common.core.domain.ValueObject
import com.univent.api.common.exception.CustomException
import com.univent.api.iam.auth.core.domain.AuthErrorCode

class RawPassword private constructor(val value: String): ValueObject() {

    init {
        validate(value)
    }

    companion object {
        private val LETTER_REGEX = Regex("[a-zA-Z]")
        private val NUMBER_REGEX = Regex("[0-9]")

        fun create(value: String): RawPassword = RawPassword(value)

        private fun validate(value: String) {
            if (value.length !in 10..30) {
                throw CustomException(AuthErrorCode.AUTH_ORGANIZATION_INVALID_PASSWORD_LENGTH)
            }

            val hasLetter = LETTER_REGEX.containsMatchIn(value)
            val hasNumber = NUMBER_REGEX.containsMatchIn(value)

            if (!hasLetter || !hasNumber) {
                throw CustomException(AuthErrorCode.AUTH_ORGANIZATION_INVALID_PASSWORD_FORMAT)
            }
        }
    }

    override fun toString(): String = "****"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is RawPassword) return false
        return value == other.value
    }

    override fun hashCode(): Int = value.hashCode()
}
