package com.r3d1r4ph.hellouser.app.di

import com.r3d1r4ph.data.user.UserDao
import com.r3d1r4ph.data.user.UserRepositoryImpl
import com.r3d1r4ph.domain.repositories.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun provideUserRepository(userDao: UserDao): UserRepository =
        UserRepositoryImpl(userDao)
}