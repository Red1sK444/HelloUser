package com.r3d1r4ph.hellouser.app.di

import com.r3d1r4ph.domain.repositories.UserRepository
import com.r3d1r4ph.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {

    @Provides
    fun provideGetUserUseCase(userRepository: UserRepository): GetUserUseCase =
        GetUserUseCaseImpl(userRepository)

    @Provides
    fun provideRegisterUserUseCase(userRepository: UserRepository): RegisterUseCase =
        RegisterUseCaseImpl(userRepository)

    @Provides
    fun provideValidateInputFieldUseCase(): ValidateInputFieldUseCase =
        ValidateInputFieldUseCaseImpl()
}