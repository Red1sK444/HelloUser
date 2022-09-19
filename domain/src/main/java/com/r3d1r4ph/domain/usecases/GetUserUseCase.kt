package com.r3d1r4ph.domain.usecases

import com.r3d1r4ph.domain.common.exceptions.NoRegisteredUserException
import com.r3d1r4ph.domain.entities.UserEntity
import com.r3d1r4ph.domain.repositories.UserRepository
import com.r3d1r4ph.domain.usecases.templates.UseCaseWithoutParam
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface GetUserUseCase : UseCaseWithoutParam<Result<UserEntity>>

class GetUserUseCaseImpl(
    private val userRepository: UserRepository
) : GetUserUseCase {
    override suspend fun execute(): Result<UserEntity> = withContext(Dispatchers.IO) {
        try {
            Result.success(userRepository.getUser())
        } catch (e: Exception) {
            Result.failure(NoRegisteredUserException())
        }
    }
}