package com.univent.api.iam.user.infrastructure

import com.univent.api.iam.user.domain.User
import com.univent.api.iam.user.domain.UserId
import com.univent.api.iam.user.domain.UserStore
import org.springframework.stereotype.Repository

@Repository
class UserStoreImpl(
    private val userJpaStore: UserJpaStore
) : UserStore {
    override fun save(user: User) {
        val userEntity = UserEntity.fromDomain(user)
        userJpaStore.save(userEntity)
    }

    override fun loadById(id: UserId): User? {
        return userJpaStore.findById(id.value).orElse(null)?.toDomain()
    }

    override fun deleteById(id: UserId) {
        userJpaStore.deleteById(id.value)
    }
}
