package com.univent.api.iam.user.infrastructure

import com.univent.api.iam.user.domain.User
import com.univent.api.iam.user.domain.UserId
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.Instant

@Entity
@Table(name = "users")
class UserEntity (
    @Id
    val id: Long,

    @Column(nullable = false)
    val email: String,

    @Column(nullable = false)
    val createdAt: Instant = Instant.now(),

    @Column(nullable = false)
    val updatedAt: Instant = Instant.now()
){
    companion object {
        fun fromDomain(user: User): UserEntity {
            return UserEntity(
                id = user.id.value,
                email = user.email,
                createdAt = user.createdAt,
                updatedAt = user.updatedAt
            )
        }
    }

    fun toDomain(): User {
        return User.of(
            id = UserId(id),
            email = email,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }
}
