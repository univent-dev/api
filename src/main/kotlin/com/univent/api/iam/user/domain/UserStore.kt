package com.univent.api.iam.user.domain

interface UserStore {
    fun save(user: User)
    fun loadById(id: UserId): User?
    fun deleteById(id: UserId)
}
