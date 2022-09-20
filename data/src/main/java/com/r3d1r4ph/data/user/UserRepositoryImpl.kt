package com.r3d1r4ph.data.user

import com.r3d1r4ph.data.user.model.User
import com.r3d1r4ph.domain.entities.UserEntity
import com.r3d1r4ph.domain.repositories.UserRepository

class UserRepositoryImpl(
    private val userDao: UserDao
) : UserRepository {
    override suspend fun registerUser(userEntity: UserEntity) {
        userDao.insert(User.fromDomain(userEntity))
    }

    override suspend fun getUser(): UserEntity =
        requireNotNull(userDao.getFirst()).toDomain()
}