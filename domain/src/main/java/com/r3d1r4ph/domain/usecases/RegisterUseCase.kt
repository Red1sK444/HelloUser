package com.r3d1r4ph.domain.usecases

import com.r3d1r4ph.domain.common.exceptions.PasswordNotEqualWithConfirmPasswordException
import com.r3d1r4ph.domain.common.exceptions.RegisterUserException
import com.r3d1r4ph.domain.entities.RegisterEntity
import com.r3d1r4ph.domain.repositories.UserRepository
import com.r3d1r4ph.domain.usecases.templates.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface RegisterUseCase : UseCase<RegisterEntity, Result<Unit>>

class RegisterUseCaseImpl(
    private val userRepository: UserRepository
) : RegisterUseCase {
    override suspend fun execute(input: RegisterEntity): Result<Unit> =
        withContext(Dispatchers.IO) {
            if (input.password != input.confirmPassword) {
                Result.failure(PasswordNotEqualWithConfirmPasswordException())
            } else {
                try {
                    Result.success(userRepository.registerUser(input.toUserEntity()))
                } catch (e: Exception) {
                    Result.failure(RegisterUserException())
                }
            }
        }
}