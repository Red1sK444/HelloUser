package com.r3d1r4ph.domain.usecases

import com.r3d1r4ph.domain.common.validation.ValidationRule
import com.r3d1r4ph.domain.usecases.templates.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface ValidateInputFieldUseCase : UseCase<ValidationRule, Result<Unit>>

class ValidateInputFieldUseCaseImpl : ValidateInputFieldUseCase {
    override suspend fun execute(input: ValidationRule): Result<Unit> =
        withContext(Dispatchers.Default) {
            input.apply()
        }
}