package com.r3d1r4ph.domain.repositories

import com.r3d1r4ph.domain.entities.UserEntity

interface UserRepository {
    suspend fun registerUser(userEntity: UserEntity)
    suspend fun getUser(): UserEntity
}