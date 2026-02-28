package com.univent.api.iam.auth.organization.domain.vo

import com.univent.api.common.core.domain.ValueObject
import com.univent.api.common.exception.CustomException
import com.univent.api.iam.auth.core.domain.AuthErrorCode

data class AccountId (val value: String): ValueObject() {
    init { validate() }

    companion object {
        private val FORMAT_REGEX = Regex("^[a-z0-9_]+$")

        fun create(value: String): AccountId {
            return AccountId(value)
        }
    }

    private fun validate() {
        require (value.length in 4..20) {
            throw CustomException(AuthErrorCode.AUTH_ORGANIZATION_INVALID_ACCOUNT_ID_LENGTH)
        }

        require (FORMAT_REGEX.matches(value)) {
            throw CustomException(AuthErrorCode.AUTH_ORGANIZATION_INVALID_ACCOUNT_ID_FORMAT)
        }
    }

    // Value Object의 동등성 보장을 위해 equals와 hashCode 구현 (또는 data class 활용)
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AccountId) return false
        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }
}
