package com.univent.api.iam.user.domain

import com.univent.api.common.core.domain.AggregateRoot
import com.univent.api.common.exception.CustomException
import com.univent.api.iam.user.domain.event.UserDeletedEvent

class User private constructor(
    id: UserId,
    email: String
) : AggregateRoot<UserId>(id) {
    var email: String = email
        private set

    init { validate() }

    companion object {
        fun create(id: UserId, email: String): User {
            return User(id, email)
        }
    }

    fun validate() {
        require(email.isNotBlank()) { CustomException(UserErrorCode.USER_EMAIL_EMPTY) }
        require(email.contains("@")) { CustomException(UserErrorCode.USER_INVALID_EMAIL_FORMAT) }
    }

    fun delete() {
        addDomainEvent(UserDeletedEvent(id.value))
    }
}
